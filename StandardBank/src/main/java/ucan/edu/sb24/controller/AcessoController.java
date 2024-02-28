/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ucan.edu.sb24.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ucan.edu.sb24.ententies.Acesso;
import ucan.edu.sb24.service.AcessoService;

/**
 *
 * @author jfk
 */
@RestController
@RequestMapping("/sb24/acessos")
public class AcessoController {

    private final AcessoService acessoService;

    public AcessoController(AcessoService acessoService) {
        this.acessoService = acessoService;
    }

    @PostMapping
    public ResponseEntity<Void> criarAcesso(@RequestBody Acesso acesso) {
        acessoService.criarAcesso(acesso);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Acesso> consultarAcessoPorId(@PathVariable int id) {
        return acessoService.consultarAcessoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizarAcesso(@PathVariable int id, @RequestBody Acesso acesso) {
        acessoService.atualizarAcesso(id, acesso);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirAcesso(@PathVariable int id) {
        acessoService.excluirAcesso(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<Acesso>> listarAcessos() {
        List<Acesso> acessos = acessoService.listarAcessos();
        return new ResponseEntity<>(acessos, HttpStatus.OK);
    }

}
