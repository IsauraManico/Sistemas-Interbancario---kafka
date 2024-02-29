package ucan.edu.bic.history_transations.services;



import ucan.edu.bic.ententies.Conta;
import ucan.edu.bic.history_transations.entities.HistoricoTransacao;

import java.util.List;

public interface HistoricoTransacaoService {

    List<HistoricoTransacao> getAllHistTransacoes();
    HistoricoTransacao getHistoricoTransacaoById(Integer id);
    List<HistoricoTransacao> findHistoricoTransacaoByConta(Conta conta);

}
