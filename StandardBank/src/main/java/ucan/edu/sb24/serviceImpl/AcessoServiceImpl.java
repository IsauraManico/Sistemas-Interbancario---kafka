/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ucan.edu.sb24.serviceImpl;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucan.edu.sb24.ententies.Acesso;
import ucan.edu.sb24.repository.AcessoRepository;
import ucan.edu.sb24.service.AcessoService;

/**
 *
 * @author jfk
 */
@Service
public class AcessoServiceImpl implements AcessoService {

    private final AcessoRepository acessoRepository;

    @Autowired
    public AcessoServiceImpl(AcessoRepository acessoRepository) {
        this.acessoRepository = acessoRepository;
    }

    @Override
    public void criarAcesso(Acesso acesso) {
        acessoRepository.save(acesso);
    }

    @Override
    public Optional<Acesso> consultarAcessoPorId(int id) {
        return acessoRepository.findById(id);
    }

    @Override
    public void atualizarAcesso(int id, Acesso acesso) {
        if (acessoRepository.existsById(id)) {
            acesso.setPkAcesso(id);
            acessoRepository.save(acesso);
        }
    }

    @Override
    public void excluirAcesso(int id) {
        acessoRepository.deleteById(id);
    }

    @Override
    public List<Acesso> listarAcessos() {
        return acessoRepository.findAll();
    }

}
