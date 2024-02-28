/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ucan.edu.emis.serviceImpl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucan.edu.emis.entidade.Banco;
import ucan.edu.emis.repositorio.BancoRepository;
import ucan.edu.emis.service.BancoService;

/**
 *
 * @author jfk
 */
@Service
public class BancoServiceImpl implements BancoService {

    private final BancoRepository bancoRepository;

    @Autowired
    public BancoServiceImpl(BancoRepository bancoRepository) {
        this.bancoRepository = bancoRepository;
    }

    @Override
    public List<Banco> getAllBancos() {
        return bancoRepository.findAll();
    }

    @Override
    public Banco getBancoById(Integer id) {
        return bancoRepository.findById(id).orElse(null);
    }

    @Override
    public Banco saveBanco(Banco banco) {
        return bancoRepository.save(banco);
    }

    @Override
    public void deleteBanco(Integer id) {
        bancoRepository.deleteById(id);
    }
}
