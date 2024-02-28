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
import ucan.edu.sb24.ententies.SaldoHistorico;
import ucan.edu.sb24.service.SaldoHistoricoService;

@RestController
@RequestMapping("/sb24/saldohistorico")
public class SaldoHistoricoController {

    private final SaldoHistoricoService saldoHistoricoService;

    public SaldoHistoricoController(SaldoHistoricoService saldoHistoricoService) {
        this.saldoHistoricoService = saldoHistoricoService;
    }

    @GetMapping("/{iban}")
    public List<SaldoHistorico> consultarSaldoHistoricoPorIban(@PathVariable String iban) {
        return saldoHistoricoService.consultarSaldoHistoricoPorIban(iban);
    }

    @GetMapping
    public List<SaldoHistorico> listarSaldoHistorico() {
        return saldoHistoricoService.listarSaldoHistorico();
    }
}
