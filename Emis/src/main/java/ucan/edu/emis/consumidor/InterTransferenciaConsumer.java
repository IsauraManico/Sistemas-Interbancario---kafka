/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ucan.edu.emis.consumidor;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ucan.edu.emis.dto.StatusENUM;
import ucan.edu.emis.dto.TransferenciaRequest;
import ucan.edu.emis.entidade.Status;
import ucan.edu.emis.entidade.Transacao;
import ucan.edu.emis.produtor.Redirecionador;
import ucan.edu.emis.service.StatusService;
import ucan.edu.emis.service.TransacaoService;

/**
 *
 * @author jfk
 */
@Service
public class InterTransferenciaConsumer {

    public InterTransferenciaConsumer(ucan.edu.emis.service.TransacaoService transacaoService, ucan.edu.emis.service.StatusService statusService) {
        this.transacaoService = transacaoService;
        this.statusService = statusService;
    }

    @Autowired
    private Redirecionador redirecionador;
    private final String key = "0123456789abcdef";
    private final TransacaoService transacaoService;
    private final StatusService statusService;

    @KafkaListener(
            topics = "${kafka.topic.inter}",
            groupId = "EMIS"
    )
    public void consume(String message) {

        try {
            TransferenciaRequest transferencia;
            transferencia = TransferenciaRequest.fromString(decryptMessage(key, message));
            StatusENUM resultado = processar(transferencia);
            System.out.println("===== TRANSFERENCIA PROCESSADA ===> " + resultado);
        } catch (Exception ex) {
            System.out.println("ERRO NA ENCRIPTACAO - INTER" + ex.getMessage());
        }

    }

    private StatusENUM processar(TransferenciaRequest transferencia) {

        if (transferencia.getStatus() == StatusENUM.CONCLUIDA.ordinal()) {
            return existTransfer(transferencia);
        } else if (transferencia.getStatus() == StatusENUM.PENDENTE.ordinal()) {
            return novaTransfer(transferencia);
        }
        //RETORNO
        return StatusENUM.CONCLUIDA;
        ///</RETORNO
    }

    private StatusENUM existTransfer(TransferenciaRequest transferencia) {

        Integer numBanco = Integer.parseInt(extractDigitsAfterAO06(transferencia.getContaOrigemIban()));
        String topicRoute = "EMIS-" + numBanco;

        Transacao transacao = transferencia.getTransacao(), transacao1;
        transacao.setDataFinal(new Date());
        transacao.setFkStatus(1);
        transacao.setPkTransacao(transferencia.getPk2());
        transacao1 = transacaoService.updateTransacao(transferencia.getPk2(), transacao);

        System.out.println(transacao1.toString() + "=EXISTING=" + transferencia.toString());

        try {
            redirecionador.sendMessage(transferencia, topicRoute);
        } catch (JsonProcessingException ex) {
            System.err.println("Erro ao enviar mensagem de erro" + ex.getMessage());
            return StatusENUM.REJEITADA;
        }
        return StatusENUM.CONCLUIDA;
    }

    private StatusENUM novaTransfer(TransferenciaRequest transferencia) {
        Status status = statusService.getStatusById(transferencia.getStatus());
        Integer numBanco = Integer.parseInt(extractDigitsAfterAO06(transferencia.getContaDestinoIban()));
        String topicRoute = "EMIS-" + numBanco;
        Transacao transacao = transferencia.getTransacao(), transacao2;
        transacao.setDataInicial(new Date());
        transacao.setFkStatus(2);

        transacao2 = transacaoService.createTransacao(transacao);
        transferencia.setPk2(transacao2.getPkTransacao());

        System.out.println(transacao2.toString() + "=NEWTRANSFER=" + transferencia.toString());

        try {
            redirecionador.sendMessage(transferencia, topicRoute);
        } catch (JsonProcessingException ex) {
            System.err.println("Erro ao enviar mensagem de erro" + ex.getMessage());
            return StatusENUM.REJEITADA;
        }

        if (transacao2 == null) {
            return StatusENUM.REJEITADA;
        }
        return StatusENUM.CONCLUIDA;

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

    private static String decryptMessage(String key, String encryptedMessage) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedMessage);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }
}
