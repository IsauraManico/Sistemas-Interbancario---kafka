/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ucan.edu.emis.repositorio;

/**
 *
 * @author jfk
 */
import org.springframework.data.jpa.repository.JpaRepository;
import ucan.edu.emis.entidade.Status;

public interface StatusRepository extends JpaRepository<Status, Integer> {
    // Se necessário, métodos de repositório adicionais podem ser adicionados aqui.
}
