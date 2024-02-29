package ucan.edu.sb24.history_transations.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ucan.edu.sb24.ententies.Conta;
import ucan.edu.sb24.history_transations.entities.HistoricoTransacao;

import java.util.List;
import java.util.Optional;

@Repository
public interface HistoricoTransacaoRepository extends JpaRepository<HistoricoTransacao,Integer>
{
    @Query("SELECT h FROM HistoricoTransacao h WHERE h.fk_conta = :conta")
   List<HistoricoTransacao> findHistoricoTransacaoByFk_conta(@Param("conta") Conta conta);


}
