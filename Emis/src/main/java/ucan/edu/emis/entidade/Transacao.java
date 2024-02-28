/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ucan.edu.emis.entidade;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author jfk
 */
@Entity
@Table(name = "transacao")
@Getter
@Setter
@ToString
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pkTransacao;

    @Column(name = "encargos")
    private Double encargos;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_inicial", nullable = false)
    private Date dataInicial;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_final")
    private Date dataFinal = new Date();

    @Column(name = "montante")
    private Double montante;

    @Column(name = "fk_status")
    private int fkStatus;

    @Column(name = "conta_origem_iban")
    private String contaOrigemIban;

    @Column(name = "conta_destino_iban")
    private String contaDestinoIban;

}
