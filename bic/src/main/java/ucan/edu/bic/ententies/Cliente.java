/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ucan.edu.bic.ententies;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ucan.edu.bic.enums.ContaRole;

/**
 *
 * @author jfk
 */
@Entity
@Table(name = "cliente")
@Getter
@Setter
@ToString
public class Cliente implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String nif;

    @Column(name = "nome")
    private String nome;

    @Column(name = "data_inicio_entidade")
    private Date dataInicioEntidade;

    private String telefone;
    private String email;
    private String morada;
    private ContaRole contaRole;
    private String password;


    public Cliente(String nome, ContaRole contaRole, String password) {
        this.nome = nome;
        this.contaRole = contaRole;
        this.password = password;
    }

    public Cliente() {
    }

    public Cliente(String nif, String nome, Date dataInicioEntidade, String email, String telefone,
                   ContaRole contaRole, String password)
    {
        this.nif = nif;
        this.nome = nome;
        this.dataInicioEntidade = dataInicioEntidade;
        this.email = email;
        this.telefone = telefone;
        this.contaRole = contaRole;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.contaRole == ContaRole.ADMIN)
            return List.of(new SimpleGrantedAuthority("ADMIN"), new SimpleGrantedAuthority("CLIENTE"));
        else return List.of(new SimpleGrantedAuthority("CLIENTE"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
