/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ucan.edu.sb24.ententies;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

/**
 *
 * @author jfk
 */
@Entity
@Table(name = "conta")
@Getter
@Setter
@ToString
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pkConta;

    @Column(name = "fk_cliente")
    private String fkCliente;

    @Column(name = "saldo_disponivel")
    private Double saldoDisponivel;

    @Column(name = "saldo_contabilistico")
    private Double saldoContabilistico;

    @Column(name = "data_criacao", nullable = false)
    @CreationTimestamp
    private Timestamp dataCriacao;

    @Column(name = "iban", unique = true, nullable = false)
    private String iban;

    private String numero_conta;
}
