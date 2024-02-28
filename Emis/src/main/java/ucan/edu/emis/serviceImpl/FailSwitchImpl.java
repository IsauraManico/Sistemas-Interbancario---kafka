/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ucan.edu.emis.serviceImpl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import ucan.edu.emis.dto.StatusENUM;
import ucan.edu.emis.entidade.Transacao;
import ucan.edu.emis.repositorio.TransacaoRepository;
import ucan.edu.emis.service.FailSwitch;
import ucan.edu.emis.service.TransacaoService;

/**
 *
 * @author jfk
 */
public class FailSwitchImpl implements FailSwitch {
    
    public FailSwitchImpl(ucan.edu.emis.service.TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
        
    }
    
    @Autowired
    private TransacaoRepository transacaoRepository;
    private final TransacaoService transacaoService;
    
    @Scheduled(fixedRate = 60000) // Agendado para ser executado a cada minuto
    public void verificarTransacoes() {
        List<Transacao> transacoes = transacaoRepository.encontrarTransacoesSemDataFinalEDiferencaMaiorQue24Horas();
        if (!transacoes.isEmpty()) {
            // Aqui você pode implementar a lógica para exibir um alerta
            System.out.println("Alerta: Existem transações  pendentes há mais de 24 horas:");
            for (Transacao transacao : transacoes) {
                transacao.setFkStatus(StatusENUM.REJEITADA.ordinal());
                transacao.setDataFinal(new Date());
                transacaoService.updateTransacao(transacao.getPkTransacao(), transacao);
                System.out.println("Anulando: Transação: " + transacao.toString());
            }
        }
    }
}