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
import ucan.edu.sb24.ententies.Conta;

public interface ContaRepository extends JpaRepository<Conta, String> {

    public Object findByIban(String iban);
    // Adicione métodos de consulta personalizados, se necessário
}
