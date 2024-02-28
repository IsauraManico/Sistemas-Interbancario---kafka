/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ucan.edu.bic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ucan.edu.bic.dto.TransferenciaRequest;
import ucan.edu.bic.service.TransferenciaService;

/**
 *
 * @author jfk
 */
@RestController
@RequestMapping("/sb24/transferir")
public class TransferenciaController {

    @Autowired
    public TransferenciaController(TransferenciaService transferenciaService) {
        this.transferenciaService = transferenciaService;
    }

    private final TransferenciaService transferenciaService;

    @PostMapping("/transferencia-interna")
    public ResponseEntity<String> transferenciaInterna(@RequestBody TransferenciaRequest transferenciaRequest) {
        String resposta = transferenciaService.transferenciaIntraBancaria(transferenciaRequest);
        return ResponseEntity.ok(resposta);
    }

    @PostMapping("/transferencia-intermediada")
    public ResponseEntity<String> transferenciaIntermediada(@RequestBody TransferenciaRequest transferenciaRequest) {
        String resposta = transferenciaService.transferenciaIntermediada(transferenciaRequest);
        return ResponseEntity.ok(resposta);
    }

}
