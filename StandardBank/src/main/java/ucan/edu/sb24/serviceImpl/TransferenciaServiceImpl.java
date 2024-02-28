/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ucan.edu.sb24.serviceImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucan.edu.sb24.dto.StatusENUM;
import ucan.edu.sb24.dto.TransferenciaRequest;
import ucan.edu.sb24.ententies.Conta;
import ucan.edu.sb24.ententies.Status;
import ucan.edu.sb24.ententies.Transacao;
import ucan.edu.sb24.producer.TransferenciaRequestProducer;
import ucan.edu.sb24.service.ContaService;
import ucan.edu.sb24.service.StatusService;
import ucan.edu.sb24.service.TransacaoService;
import ucan.edu.sb24.service.TransferenciaService;

/**
 *
 * @author jfk
 */
@Service
public class TransferenciaServiceImpl implements TransferenciaService {

    public TransferenciaServiceImpl(ucan.edu.sb24.service.ContaService contaService, ucan.edu.sb24.service.TransacaoService transacaoService, ucan.edu.sb24.service.StatusService statusService) {
        this.contaService = contaService;
        this.transacaoService = transacaoService;
        this.statusService = statusService;
    }

    private final ContaService contaService;
    private final TransacaoService transacaoService;
    private final StatusService statusService;
    @Autowired
    private TransferenciaRequestProducer transferenciaRequestProducer;

    @Override
    public String transferenciaIntraBancaria(TransferenciaRequest transferenciaRequest) {
        Conta ordenante, beneficiario;
        double valor;
        Transacao transacao = new Transacao();
        StatusENUM sENUM = StatusENUM.CONCLUIDA;
        Status status = statusService.getStatusById(sENUM.ordinal());

        ordenante = contaService.obterContaPorIban(transferenciaRequest.getContaOrigemIban());
        beneficiario = contaService.obterContaPorIban(transferenciaRequest.getContaDestinoIban());
        valor = transferenciaRequest.getEncargos() + transferenciaRequest.getMontante();

        if (ordenante == null || beneficiario == null) {
            return "CONTA NOT FOUND \n Ordenante-> " + ordenante + "\n Benificiario->" + beneficiario;
        }
        if (ordenante.getSaldoDisponivel() < valor) {
            return "LOW BALLANCE \n Saldo insuficiente";
        }
        if (!sameBank(ordenante, beneficiario)) {
            System.out.println("Erro de integridade do sistema");
            return transferenciaIntermediada(transferenciaRequest);
        }

        //Alterar balanço das contas
        ordenante.setSaldoContabilistico(ordenante.getSaldoContabilistico() - valor);
        ordenante.setSaldoDisponivel(ordenante.getSaldoDisponivel() - valor);

        beneficiario.setSaldoContabilistico(beneficiario.getSaldoContabilistico() + transferenciaRequest.getMontante());
        beneficiario.setSaldoDisponivel(beneficiario.getSaldoDisponivel() + transferenciaRequest.getMontante());

        contaService.atualizarConta(ordenante.getPkConta(), ordenante);
        contaService.atualizarConta(beneficiario.getPkConta(), beneficiario);
        transacao.setContaDestinoIban(transferenciaRequest.getContaDestinoIban());
        transacao.setContaOrigemIban(transferenciaRequest.getContaOrigemIban());
        transacao.setEncargos(transferenciaRequest.getEncargos());
        transacao.setMontante(transferenciaRequest.getMontante());
        transacao.setFkStatus(status);
        transacao.setDataInicial(new Date());
        transacao.setDataFinal(new Date());
        transacaoService.createTransacao(transacao);
        return "Sucesso!";
    }

    @Override
    public String transferenciaIntermediada(TransferenciaRequest transferenciaRequest) {
        Conta beneficiario = new Conta(), ordenante = contaService.obterContaPorIban(transferenciaRequest.getContaOrigemIban());
        double valor = transferenciaRequest.getEncargos() + transferenciaRequest.getMontante();
        Transacao transacao = new Transacao();
        StatusENUM sENUM = StatusENUM.PENDENTE;
        Status status = statusService.getStatusById(sENUM.ordinal());
        if (ordenante == null) {
            return "CONTA NOT FOUND \n Ordenante-> " + ordenante;
        }
        if (ordenante.getSaldoDisponivel() < valor) {
            return "LOW BALLANCE \n Saldo insuficiente";
        }

        ordenante.setSaldoDisponivel(ordenante.getSaldoDisponivel() - valor);
        contaService.atualizarConta(ordenante.getPkConta(), ordenante);

        transacao.setContaDestinoIban(transferenciaRequest.getContaDestinoIban());
        transacao.setContaOrigemIban(transferenciaRequest.getContaOrigemIban());
        transacao.setEncargos(transferenciaRequest.getEncargos());
        transacao.setMontante(transferenciaRequest.getMontante());
        transacao.setFkStatus(status);
        transacao.setDataInicial(new Date());
        transacao = transacaoService.createTransacao(transacao);

        beneficiario.setIban(transferenciaRequest.getContaDestinoIban());
        transferenciaRequest.setPk(transacao.getPkTransacao());
        transferenciaRequest.setStatus(transacao.getFkStatus().getPkStatus());
        return integrarTransferencia(transferenciaRequest, sameBank(ordenante, beneficiario));
    }

    @Override
    public String integrarTransferencia(TransferenciaRequest transferenciaRequest, Boolean sameBank) {
        try {
            if (sameBank) {
                return transferenciaRequestProducer.sendMessageIntra(transferenciaRequest);
            }
            return transferenciaRequestProducer.sendMessageInter(transferenciaRequest);

        } catch (JsonProcessingException e) {
            return "Houve um erro ao solicitar transferencia " + e.getMessage();
        }
    }

    public static String extractDigitsAfterAO06(String iban) {
        Pattern pattern = Pattern.compile("AO06(\\d{4})");
        Matcher matcher = pattern.matcher(iban);

        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return "Não foi possível encontrar os 4 dígitos após 'AO06'\n" + iban;
        }
    }

    public static boolean sameBank(Conta conta1, Conta conta2) {
        return extractDigitsAfterAO06(conta1.getIban()).equals(extractDigitsAfterAO06(conta2.getIban()));
    }

}