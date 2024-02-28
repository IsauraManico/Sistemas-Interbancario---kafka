/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ucan.edu.bic.serviceImpl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucan.edu.bic.repository.ClienteRepository;
import ucan.edu.bic.ententies.Cliente;
import ucan.edu.bic.service.ClienteService;

/**
 *
 * @author jfk
 */
@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    @Autowired
    public ClienteServiceImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente obterClientePorNif(String nif) {
        return clienteRepository.findById(nif).orElse(null);
    }

    @Override
    public void cadastrarCliente(Cliente cliente) {
        clienteRepository.save(cliente);
    }

    @Override
    public void atualizarCliente(String nif, Cliente cliente) {
        if (clienteRepository.existsById(nif)) {
            cliente.setNif(nif); // Garante que a chave primária seja mantida
            clienteRepository.save(cliente);
        }
    }

    @Override
    public void excluirCliente(String nif) {
        clienteRepository.deleteById(nif);
    }
}
