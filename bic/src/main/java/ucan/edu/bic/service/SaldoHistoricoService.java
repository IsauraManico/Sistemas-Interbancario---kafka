/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ucan.edu.bic.service;

import java.util.List;
import ucan.edu.bic.ententies.SaldoHistorico;

/**
 *
 * @author jfk
 */
public interface SaldoHistoricoService {

    List<SaldoHistorico> consultarSaldoHistoricoPorIban(String iban);

    List<SaldoHistorico> listarSaldoHistorico();
}
