/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ucan.edu.bic.service;

import java.util.List;
import ucan.edu.bic.ententies.Conta;

/**
 *
 * @author jfk
 */
public interface ContaService {

    List<Conta> listarContas();

    Conta getContaById(int pkConta);

    Conta obterContaPorIban(String iban);

    void cadastrarConta(Conta conta);

    void atualizarConta(int pkConta, Conta conta);

    void excluirConta(int pkConta);

}
