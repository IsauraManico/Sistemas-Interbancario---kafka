/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ucan.edu.bic.ententies;

import java.util.Date;

/**
 *
 * @author jfk
 */
public class SaldoHistorico {

    private String iban;
    private double saldo;
    private Date dataRegistro;

    public SaldoHistorico() {
    }

    public SaldoHistorico(String iban, double saldo, Date dataRegistro) {
        this.iban = iban;
        this.saldo = saldo;
        this.dataRegistro = dataRegistro;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public Date getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(Date dataRegistro) {
        this.dataRegistro = dataRegistro;
    }
}
