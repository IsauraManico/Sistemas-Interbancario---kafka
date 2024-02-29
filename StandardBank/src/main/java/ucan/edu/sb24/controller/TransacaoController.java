/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ucan.edu.sb24.controller;

/**
 *
 * @author jfk
 */
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ucan.edu.sb24.ententies.Transacao;
import ucan.edu.sb24.service.TransacaoService;

@RestController
@RequestMapping("/sb24/transacoes")
public class TransacaoController {

    private final TransacaoService transacaoService;

    @Autowired
    public TransacaoController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    @GetMapping("/listarNoBanco")
    public ResponseEntity<List<Transacao>> getAllTransacoes() {
        List<Transacao> transacoes = transacaoService.getAllTransacoes();
        return new ResponseEntity<>(transacoes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transacao> getTransacaoById(@PathVariable int id) {
        Transacao transacao = transacaoService.getTransacaoById(id);
        return new ResponseEntity<>(transacao, HttpStatus.OK);
    }
    /*
    @PostMapping
    public ResponseEntity<Transacao> createTransacao(@RequestBody Transacao transacao) {
        Transacao createdTransacao = transacaoService.createTransacao(transacao);
        return new ResponseEntity<>(createdTransacao, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transacao> updateTransacao(@PathVariable int id, @RequestBody Transacao transacao) {
        Transacao updatedTransacao = transacaoService.updateTransacao(id, transacao);
        return new ResponseEntity<>(updatedTransacao, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransacao(@PathVariable int id) {
        transacaoService.deleteTransacao(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
 
     */
}
