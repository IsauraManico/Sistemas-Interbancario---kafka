/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ucan.edu.sb24.serviceImpl;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucan.edu.sb24.ententies.Conta;
import ucan.edu.sb24.repository.ContaRepository;
import ucan.edu.sb24.service.ContaService;

/**
 *
 * @author jfk
 */
@Service
public class ContaServiceImpl implements ContaService {

    private final ContaRepository contaRepository;

    @Autowired
    public ContaServiceImpl(ContaRepository contaRepository) {
        this.contaRepository = contaRepository;
    }

    String codigoBanco = "0006"; //"20507";

    private String gerarNumeroConta() {

        Random random = new Random();
        int numero = 10000000 + random.nextInt(90000000);
        return String.valueOf(numero);
    }

    private String gerarIBAN(String numeroConta) {

        String pais = "AO06";

        String digitoVerificador = "101";
        System.out.println("O valor da conta "+numeroConta);
        return pais  + codigoBanco + numeroConta + digitoVerificador;

    }

    public boolean verificarCodigoBancoNoIBAN(String iban, String codigoBancoEsperado) {

        if (iban == null || iban.length() < 8) {
            return false;
        }
        String codigoBancoExtraido = iban.substring(4, 8);
        return codigoBancoExtraido.equals(codigoBancoEsperado);
    }



    @Override
    public List<Conta> listarContas() {
        return contaRepository.findAll();
    }

    @Override
    public Conta obterContaPorIban(String iban) {
        return (Conta) contaRepository.findByIban(iban);
    }

    @Override
    public void cadastrarConta(Conta conta)
    {
        conta.setNumero_conta(gerarNumeroConta());
        conta.setIban(gerarIBAN(conta.getNumero_conta()));
        contaRepository.save(conta);
    }

    @Override
    public void atualizarConta(int pk_conta, Conta conta) {
        if (contaRepository.existsById(pk_conta + "")) {
            conta.setPkConta(pk_conta);
            contaRepository.save(conta);
        }
    }

    @Override
    public void excluirConta(int pkConta) {
        contaRepository.deleteById(pkConta + "");

    }

    @Override
    public Conta getContaById(int pkConta) {
        return contaRepository.findById(pkConta + "").orElse(null);
    }
}
