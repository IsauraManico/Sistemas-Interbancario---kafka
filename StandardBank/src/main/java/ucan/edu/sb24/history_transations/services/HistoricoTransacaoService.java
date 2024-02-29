package ucan.edu.sb24.history_transations.services;


import ucan.edu.sb24.ententies.Conta;
import ucan.edu.sb24.history_transations.entities.HistoricoTransacao;

import java.util.List;

public interface HistoricoTransacaoService {

    List<HistoricoTransacao> getAllHistTransacoes();
    HistoricoTransacao getHistoricoTransacaoById(Integer id);
    List<HistoricoTransacao> findHistoricoTransacaoByConta(Conta conta);

}
