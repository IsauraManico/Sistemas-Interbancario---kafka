/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ucan.edu.emis.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import ucan.edu.emis.entidade.Banco;

/**
 *
 * @author jfk
 */
public interface BancoRepository extends JpaRepository<Banco, Integer> {
    // Pode adicionar consultas personalizadas se necessário
}
