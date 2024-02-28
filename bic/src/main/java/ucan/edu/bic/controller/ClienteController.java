/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ucan.edu.bic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ucan.edu.bic.ententies.Cliente;
import ucan.edu.bic.service.ClienteService;
import ucan.edu.bic.serviceImpl.ClienteServiceImpl;

/**
 *
 * @author jfk
 */
import java.util.List;

@RestController
@RequestMapping("/sb24/clientes")
public class ClienteController {

    private final ClienteServiceImpl clienteService;

    @Autowired
    public ClienteController(ClienteServiceImpl clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public List<Cliente> listarClientes() {
        return clienteService.listarClientes();
    }

    @GetMapping("/{nif}")
    public Cliente obterClientePorNif(@PathVariable String nif) {
        return clienteService.obterClientePorNif(nif);
    }

    @PostMapping
    public void cadastrarCliente(@RequestBody Cliente cliente) {
        clienteService.cadastrarCliente(cliente);
    }

    @PutMapping("/{nif}")
    public void atualizarCliente(@PathVariable String nif, @RequestBody Cliente cliente) {
        clienteService.atualizarCliente(nif, cliente);
    }

    @DeleteMapping("/{nif}")
    public void excluirCliente(@PathVariable String nif) {
        clienteService.excluirCliente(nif);
    }
}
