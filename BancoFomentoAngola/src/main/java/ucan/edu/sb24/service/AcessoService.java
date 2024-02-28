/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ucan.edu.sb24.service;

import java.util.List;
import java.util.Optional;
import ucan.edu.sb24.ententies.Acesso;

/**
 *
 * @author jfk
 */
public interface AcessoService {

    void criarAcesso(Acesso acesso);

    Optional<Acesso> consultarAcessoPorId(int id);

    void atualizarAcesso(int id, Acesso acesso);

    void excluirAcesso(int id);

    List<Acesso> listarAcessos();

    // Outros métodos conforme necessário
}
