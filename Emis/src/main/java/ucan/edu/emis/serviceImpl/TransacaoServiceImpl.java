/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ucan.edu.emis.serviceImpl;

import java.util.List;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucan.edu.emis.entidade.Transacao;
import ucan.edu.emis.repositorio.TransacaoRepository;
import ucan.edu.emis.service.TransacaoService;

/**
 *
 * @author jfk
 */
@Service
public class TransacaoServiceImpl implements TransacaoService {

    private final TransacaoRepository transacaoRepository;

    @Autowired
    public TransacaoServiceImpl(TransacaoRepository transacaoRepository) {
        this.transacaoRepository = transacaoRepository;
    }

    @Override
    public List<Transacao> getAllTransacoes() {
        return transacaoRepository.findAll();
    }

    @Override
    public Transacao getTransacaoById(int id) {
        return transacaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transacao not found with id: " + id));
    }

    @Override
    public Transacao createTransacao(Transacao transacao) {
        // Lógica para criar uma nova Transacao (por exemplo, validações podem ser adicionadas).
        return transacaoRepository.save(transacao);
    }

    @Override
    public Transacao updateTransacao(int id, Transacao transacao) {
        // Lógica para atualizar uma Transacao existente (por exemplo, validações podem ser adicionadas).
        Transacao existingTransacao = getTransacaoById(id);
        existingTransacao.setMontante(transacao.getMontante());
        existingTransacao.setEncargos(transacao.getEncargos());
        existingTransacao.setContaDestinoIban(transacao.getContaDestinoIban());
        existingTransacao.setContaOrigemIban(transacao.getContaOrigemIban());
        existingTransacao.setDataFinal(transacao.getDataFinal());
        existingTransacao.setFkStatus(transacao.getFkStatus());
        return transacaoRepository.save(existingTransacao);
    }

    @Override
    public void deleteTransacao(int id) {
        // Lógica para excluir uma Transacao (por exemplo, verifique se a Transacao existe antes de excluí-la).
        Transacao transacao = getTransacaoById(id);
        transacaoRepository.delete(transacao);
    }

}
