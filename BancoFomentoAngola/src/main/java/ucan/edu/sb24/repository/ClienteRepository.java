/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ucan.edu.sb24.repository;

/**
 *
 * @author jfk
 */
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import ucan.edu.sb24.ententies.Cliente;
import ucan.edu.sb24.enums.ContaRole;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, String> {

    Optional<Cliente> findFirstByEmail(String email);
    Cliente findByContaRole(ContaRole userRole);
    UserDetails findByNome(String nome);

}


