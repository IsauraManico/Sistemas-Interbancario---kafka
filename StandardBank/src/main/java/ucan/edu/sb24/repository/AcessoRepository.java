/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ucan.edu.sb24.repository;

/**
 *
 * @author jfk
 */
import org.springframework.data.jpa.repository.JpaRepository;
import ucan.edu.sb24.ententies.Acesso;

public interface AcessoRepository extends JpaRepository<Acesso, Integer> {

    Acesso findByNumAcesso(String numAcesso);
}
