/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ucan.edu.bic.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import ucan.edu.bic.ententies.SaldoHistorico;
import ucan.edu.bic.service.SaldoHistoricoService;

/**
 *
 * @author jfk
 */
@Service
public class SaldoHistoricoServiceImpl implements SaldoHistoricoService {

    private final Map<String, List<SaldoHistorico>> historicosPorIban = new HashMap<>();

    @Override
    public List<SaldoHistorico> consultarSaldoHistoricoPorIban(String iban) {
        return historicosPorIban.getOrDefault(iban, new ArrayList<>());
    }

    @Override
    public List<SaldoHistorico> listarSaldoHistorico() {
        return historicosPorIban.values().stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    // Método para adicionar um novo histórico
    public void adicionarSaldoHistorico(String iban, SaldoHistorico saldoHistorico) {
        List<SaldoHistorico> historico = historicosPorIban.getOrDefault(iban, new ArrayList<>());
        historico.add(saldoHistorico);
        historicosPorIban.put(iban, historico);
    }
}
