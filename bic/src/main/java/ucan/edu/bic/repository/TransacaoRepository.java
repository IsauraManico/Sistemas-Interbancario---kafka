/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ucan.edu.bic.repository;

/**
 *
 * @author jfk
 */
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ucan.edu.bic.ententies.Transacao;

public interface TransacaoRepository extends JpaRepository<Transacao, Integer> {

    @Query(value = "SELECT * FROM transacao WHERE "
            + "EXTRACT(HOUR FROM (current_timestamp - data_inicial)) > 24 AND "
            + "fk_status=2", nativeQuery = true)
    List<Transacao> encontrarTransacoesSemDataFinalEDiferencaMaiorQue24Horas();

}
