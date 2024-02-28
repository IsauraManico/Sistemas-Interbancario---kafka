/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ucan.edu.emis.service;

import java.util.List;
import ucan.edu.emis.entidade.Transacao;

/**
 *
 * @author jfk
 */
public interface TransacaoService {

    List<Transacao> getAllTransacoes();

    Transacao getTransacaoById(int id);

    Transacao createTransacao(Transacao transacao);

    Transacao updateTransacao(int id, Transacao transacao);

    void deleteTransacao(int id);

}
