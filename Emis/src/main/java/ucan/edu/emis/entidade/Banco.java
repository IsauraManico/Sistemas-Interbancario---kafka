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
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import jakarta.persistence.Table;

/**
 *
 * @author jfk
 */
@Getter
@Setter
@ToString
@Entity
@Table(name = "banco")
public class Banco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_banco")
    private Integer pkBanco;

    @Column(name = "nome")
    private String nome;

    @Column(name = "codigo_identificacao")
    private Integer codigoIdentificacao;

    @Column(name = "sede")
    private String sede;

    @Column(name = "rua")
    private String rua;

    @Column(name = "bairro")
    private String bairro;

    @Column(name = "provincia")
    private String provincia;

    @Column(name = "email")
    private String email;

}
