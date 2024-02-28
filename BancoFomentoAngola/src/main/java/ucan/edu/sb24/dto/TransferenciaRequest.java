/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ucan.edu.sb24.dto;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author jfk
 */
@Getter
@Setter
public class TransferenciaRequest {

    private int pk = 0;
    private String contaOrigemIban;
    private String contaDestinoIban;
    private double encargos = 0.0;
    private double montante = 0.0;
    private int status = StatusENUM.PENDENTE.ordinal();
    private int pk2 = 0;

    public TransferenciaRequest() {
    }

    @Override
    public String toString() {
        return pk
                + "," + contaOrigemIban
                + "," + contaDestinoIban
                + "," + encargos
                + "," + montante
                + "," + status
                + "," + pk2;
    }
    // Método para converter uma string para TransferenciaRequest

    public static TransferenciaRequest fromString(String inputString) {
        String[] partes = inputString.split(",");

        TransferenciaRequest transferencia = new TransferenciaRequest();
        transferencia.setPk(Integer.parseInt(partes[0].trim()));
        transferencia.setContaOrigemIban(partes[1].trim());
        transferencia.setContaDestinoIban(partes[2].trim());
        transferencia.setEncargos(Double.parseDouble(partes[3].trim()));
        transferencia.setMontante(Double.parseDouble(partes[4].trim()));
        transferencia.setStatus(Integer.parseInt(partes[5].trim()));
        transferencia.setPk2(Integer.parseInt(partes[6].trim()));

        return transferencia;
    }

}
