/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ucan.edu.bic.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ucan.edu.bic.dto.StatusENUM;
import ucan.edu.bic.dto.TransferenciaRequest;
import ucan.edu.bic.ententies.Status;
import ucan.edu.bic.ententies.Transacao;
import ucan.edu.bic.service.ContaService;
import ucan.edu.bic.service.StatusService;
import ucan.edu.bic.service.TransacaoService;
import ucan.edu.bic.ententies.Conta;
import ucan.edu.bic.producer.TransferenciaRequestProducer;

/**
 *
 * @author jfk
 */
@Service
public class TransferenciaRequestConsumer {

    public TransferenciaRequestConsumer(ContaService contaService, TransacaoService transacaoService, StatusService statusService) {
        this.contaService = contaService;
        this.transacaoService = transacaoService;
        this.statusService = statusService;
    }
    private final String key = "0123456789abcdef";
    private final ContaService contaService;
    private final TransacaoService transacaoService;
    private final StatusService statusService;

    @Autowired
    private TransferenciaRequestProducer transferenciaProducer;

    @KafkaListener(
            topics = "${kafka.topic.listner}",
            groupId = "EMIS"
    )
    public void consume(String message) {

        try {
            TransferenciaRequest transferencia;
            transferencia = TransferenciaRequest.fromString(decryptMessage(key, message));
            StatusENUM resultado = processarStatus(transferencia, getStatusByNumero(transferencia.getStatus()));
            System.out.println("===== TRANSFERENCIA PROCESSADA ===> " + resultado);
        } catch (Exception ex) {
            System.out.println("ERRO NA ENCRIPTACAO - TRANS-CONSUMER" + ex.getMessage());
        }

    }

    private StatusENUM processarStatus(TransferenciaRequest transferencia, StatusENUM status) {
        return switch (status) {
            case PENDENTE ->
                creditar(transferencia);
            case CONCLUIDA ->
                debitar(transferencia);
            case REJEITADA ->
                anular(transferencia);
            default ->
                StatusENUM.NULA;
        };
    }

    private StatusENUM anular(TransferenciaRequest transferencia) {
        Conta conta = contaService.obterContaPorIban(transferencia.getContaOrigemIban());
        Status status = statusService.getStatusById(StatusENUM.REJEITADA.ordinal());

        if (conta == null) {
            System.err.println("Erro de integridade no sistema");
            return StatusENUM.REJEITADA;
        }
        conta.setSaldoDisponivel(transferencia.getEncargos() + transferencia.getMontante());
        contaService.atualizarConta(conta.getPkConta(), conta);
        return StatusENUM.REJEITADA;
    }

    private StatusENUM debitar(TransferenciaRequest transferencia) {
        Conta conta = contaService.obterContaPorIban(transferencia.getContaOrigemIban());
        Transacao transacao = transacaoService.getTransacaoById(transferencia.getPk());
        StatusENUM sENUM = StatusENUM.CONCLUIDA;
        Status status = statusService.getStatusById(sENUM.ordinal());

        if (conta == null) {
            return StatusENUM.REJEITADA;
        }

        transacao.setFkStatus(status);
        transacao.setDataFinal(new Date());
        conta.setSaldoContabilistico(conta.getSaldoContabilistico() - transferencia.getEncargos() - transferencia.getMontante());
        transacao.setPkTransacao(transferencia.getPk());
        contaService.atualizarConta(conta.getPkConta(), conta);
        transacaoService.updateTransacao(transferencia.getPk(), transacao);

        return StatusENUM.CONCLUIDA;
    }

    private StatusENUM creditar(TransferenciaRequest transferencia) {
        Conta conta = contaService.obterContaPorIban(transferencia.getContaDestinoIban());
        Transacao transacao = new Transacao();
        StatusENUM sENUM = StatusENUM.CONCLUIDA;
        Status status = statusService.getStatusById(sENUM.ordinal());

        if (conta == null) {
            try {
                transferencia.setStatus(StatusENUM.REJEITADA.ordinal());
                transferenciaProducer.sendMessageError404(transferencia);
                return StatusENUM.REJEITADA;
            } catch (JsonProcessingException e) {
                System.err.println("Houve um erro ao reportar o erro " + e.getMessage());
                return StatusENUM.REJEITADA;
            }
        }

        conta.setSaldoContabilistico(conta.getSaldoContabilistico() + transferencia.getMontante());
        conta.setSaldoDisponivel(conta.getSaldoDisponivel() + transferencia.getMontante());

        transacao.setContaDestinoIban(transferencia.getContaDestinoIban());
        transacao.setContaOrigemIban(transferencia.getContaOrigemIban());
        transacao.setEncargos(0.0);
        transacao.setMontante(transferencia.getMontante());
        transacao.setFkStatus(status);

        transacao.setDataFinal(new Date());
        transacao.setDataInicial(new Date());
        if (sameBank(transferencia)) {
            try {
                contaService.atualizarConta(conta.getPkConta(), conta);
                transferencia.setStatus(StatusENUM.CONCLUIDA.ordinal());
                transferenciaProducer.sendMessageIntra(transferencia);
            } catch (JsonProcessingException ex) {
                System.err.println("Houve um erro concluir INTRAtransferencia" + ex.getMessage());
                return StatusENUM.REJEITADA;
            }
            return StatusENUM.CONCLUIDA;
        }

        try {
            contaService.atualizarConta(conta.getPkConta(), conta);
            transferencia.setStatus(StatusENUM.CONCLUIDA.ordinal());
            transferenciaProducer.sendMessageInter(transferencia);
            transacaoService.createTransacao(transacao);
            return StatusENUM.CONCLUIDA;
        } catch (JsonProcessingException ex) {
            System.err.println("Houve um erro concluir INTERtransferencia" + ex.getMessage());
            return StatusENUM.REJEITADA;
        }

    }

    private static String extractDigitsAfterAO06(String iban) {
        Pattern pattern = Pattern.compile("AO06(\\d{4})");
        Matcher matcher = pattern.matcher(iban);

        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return "Não foi possível encontrar os 4 dígitos após 'AO06'\n" + iban;
        }
    }

    private static boolean sameBank(TransferenciaRequest transferencia) {
        return extractDigitsAfterAO06(transferencia.getContaOrigemIban()).equals(extractDigitsAfterAO06(transferencia.getContaDestinoIban()));
    }

    private static StatusENUM getStatusByNumero(int numero) {
        // Obtendo todos os valores do enum
        StatusENUM[] values = StatusENUM.values();

        // Certificando-se de que o número está dentro dos limites
        if (numero >= 0 && numero < values.length) {
            return values[numero];
        } else {
            throw new IllegalArgumentException("Número de status inválido: " + numero);
        }
    }

    private static String decryptMessage(String key, String encryptedMessage) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedMessage);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }
}
