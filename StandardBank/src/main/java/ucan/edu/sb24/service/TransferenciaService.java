/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ucan.edu.sb24.service;

import ucan.edu.sb24.dto.TransferenciaRequest;
import ucan.edu.sb24.ententies.Transacao;

/**
 *
 * @author jfk
 */
public interface TransferenciaService {

    String transferenciaIntraBancaria(TransferenciaRequest transferenciaRequest);

    String transferenciaIntermediada(TransferenciaRequest transferenciaRequest);

    public String integrarTransferencia(TransferenciaRequest transferenciaRequest, Boolean sameBank);
}
