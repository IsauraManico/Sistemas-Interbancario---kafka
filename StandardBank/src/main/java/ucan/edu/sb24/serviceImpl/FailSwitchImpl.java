/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ucan.edu.sb24.serviceImpl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ucan.edu.sb24.dto.StatusENUM;
import ucan.edu.sb24.ententies.Conta;
import ucan.edu.sb24.ententies.Transacao;
import ucan.edu.sb24.repository.TransacaoRepository;
import ucan.edu.sb24.service.ContaService;
import ucan.edu.sb24.service.FailSwitch;
import ucan.edu.sb24.service.StatusService;
import ucan.edu.sb24.service.TransacaoService;

/**
 *
 * @author jfk
 */
@Service
public class FailSwitchImpl implements FailSwitch {
    
    public FailSwitchImpl(ucan.edu.sb24.service.TransacaoService transacaoService, ucan.edu.sb24.service.StatusService statusService, ucan.edu.sb24.service.ContaService contaService) {
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
