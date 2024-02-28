/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ucan.edu.sb24.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ucan.edu.sb24.ententies.Conta;
import ucan.edu.sb24.service.ContaService;
import ucan.edu.sb24.service.TransacaoService;

/**
 *
 * @author jfk
 */
@RestController
@RequestMapping("/sb24/contas")
public class ContaController {

//    private final TransacaoService transacaoService;
    private final ContaService contaService;

    @Autowired
    public ContaController(ContaService contaService) {
        this.contaService = contaService;
    }

    @GetMapping
    public List<Conta> listarContas() {
        return contaService.listarContas();
    }

    @GetMapping("/{parametro}")
    public Conta obterConta(@PathVariable String parametro) {
        //retorna null se não encontrar...
        if (parametro.matches("\\d+")) {
            int numero = Integer.parseInt(parametro);
            return contaService.getContaById(numero);
        } else {
            return contaService.obterContaPorIban(parametro);
        }
    }

    @PostMapping
    public void cadastrarConta(@RequestBody Conta conta) {
        contaService.cadastrarConta(conta);
    }

    @PutMapping("/{pkConta}")
    public void atualizarConta(@PathVariable int pkConta, @RequestBody Conta conta) {
        contaService.atualizarConta(pkConta, conta);
    }

    @DeleteMapping("/{pkConta}")
    public void excluirConta(@PathVariable int pkConta) {
        contaService.excluirConta(pkConta);
    }
}
