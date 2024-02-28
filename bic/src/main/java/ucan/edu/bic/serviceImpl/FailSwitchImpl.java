/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ucan.edu.bic.serviceImpl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ucan.edu.bic.repository.TransacaoRepository;
import ucan.edu.bic.dto.StatusENUM;
import ucan.edu.bic.ententies.Conta;
import ucan.edu.bic.ententies.Transacao;
import ucan.edu.bic.service.ContaService;
import ucan.edu.bic.service.FailSwitch;
import ucan.edu.bic.service.StatusService;
import ucan.edu.bic.service.TransacaoService;

/**
 *
 * @author jfk
 */
@Service
public class FailSwitchImpl implements FailSwitch {
    
    public FailSwitchImpl(TransacaoService transacaoService, StatusService statusService, ContaService contaService) {
        this.transacaoService = transacaoService;
        this.statusService = statusService;  
        this.contaService = contaService;
    }
    
    @Autowired
    private TransacaoRepository transacaoRepository;
    private final TransacaoService transacaoService;
    private final StatusService statusService;
    private final ContaService contaService;
    
    @Scheduled(fixedRate = 600000) // Agendado para ser executado a cada hora
    public void verificarTransacoes() {
        List<Transacao> transacoes = transacaoRepository.encontrarTransacoesSemDataFinalEDiferencaMaiorQue24Horas();
        Conta conta;
        if (!transacoes.isEmpty()) {
            // Aqui você pode implementar a lógica para exibir um alerta
            System.out.println("Alerta: Existem transações  pendentes há mais de 24 horas:");
            for (Transacao transacao : transacoes) {
                
                transacao.setFkStatus(statusService.getStatusById(StatusENUM.REJEITADA.ordinal()));
                transacao.setDataFinal(new Date());
                conta = contaService.obterContaPorIban(transacao.getContaOrigemIban());
                transacaoService.updateTransacao(transacao.getPkTransacao(), transacao);
                contaService.atualizarConta(conta.getPkConta(), conta);
                System.out.println("Anulando: Transação: " + transacao.toString());
            }
        }
    }
}
