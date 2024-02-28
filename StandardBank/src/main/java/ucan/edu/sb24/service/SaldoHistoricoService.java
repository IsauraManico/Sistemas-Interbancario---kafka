/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ucan.edu.sb24.service;

import java.util.List;
import ucan.edu.sb24.ententies.SaldoHistorico;

/**
 *
 * @author jfk
 */
public interface SaldoHistoricoService {

    List<SaldoHistorico> consultarSaldoHistoricoPorIban(String iban);

    List<SaldoHistorico> listarSaldoHistorico();
}
