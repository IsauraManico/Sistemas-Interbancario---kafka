/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ucan.edu.sb24.service;

import java.util.List;
import ucan.edu.sb24.ententies.Cliente;

/**
 *
 * @author jfk
 */
public interface ClienteService {

    List<Cliente> listarClientes();

    Cliente obterClientePorNif(String nif);

    void cadastrarCliente(Cliente cliente);

    void atualizarCliente(String nif, Cliente cliente);

    void excluirCliente(String nif);
}
