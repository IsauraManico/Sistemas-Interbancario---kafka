/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ucan.edu.bic.ententies;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.sql.Timestamp;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;

/**
 *
 * @author jfk
 */
@Entity
@Table(name = "acesso")
@Getter
@Setter
@ToString
public class Acesso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pkAcesso;

    @Column(name = "num_acesso", unique = true, nullable = false)
    private String numAcesso;

    @Column(name = "senha")
    private String senha;

    @Column(name = "data_criacao", nullable = false)
    @CreationTimestamp
    private Timestamp dataCriacao;

    @ManyToOne
    @JoinColumn(name = "fk_conta")
    private Conta conta;
}
