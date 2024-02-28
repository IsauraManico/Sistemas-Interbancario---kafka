/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ucan.edu.emis.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ucan.edu.emis.entidade.Banco;
import ucan.edu.emis.service.BancoService;

/**
 *
 * @author jfk
 */
@RestController
@RequestMapping("/bancos")
public class BancoController {

    private final BancoService bancoService;

    @Autowired
    public BancoController(BancoService bancoService) {
        this.bancoService = bancoService;
    }

    @GetMapping
    public List<Banco> getAllBancos() {
        return bancoService.getAllBancos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Banco> getBancoById(@PathVariable Integer id) {
        Banco banco = bancoService.getBancoById(id);
        return ResponseEntity.ok().body(banco);
    }

    @PostMapping
    public ResponseEntity<Banco> saveBanco(@RequestBody Banco banco) {
        Banco savedBanco = bancoService.saveBanco(banco);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBanco);
    }
    /*
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBanco(@PathVariable Integer id) {
        bancoService.deleteBanco(id);
        return ResponseEntity.noContent().build();
    }*/
}
