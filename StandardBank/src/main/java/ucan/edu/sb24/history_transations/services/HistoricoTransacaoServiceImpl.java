package ucan.edu.sb24.history_transations.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucan.edu.sb24.ententies.Conta;
import ucan.edu.sb24.history_transations.entities.HistoricoTransacao;
import ucan.edu.sb24.history_transations.repositories.HistoricoTransacaoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class HistoricoTransacaoServiceImpl implements HistoricoTransacaoService{

    @Autowired
    private HistoricoTransacaoRepository historicoTransacaoRepository;
    @Override
    public List<HistoricoTransacao> getAllHistTransacoes() {
        return historicoTransacaoRepository.findAll();
    }

    @Override
    public HistoricoTransacao getHistoricoTransacaoById(Integer id) {
        Optional<HistoricoTransacao> historicoTransacao = historicoTransacaoRepository.findById(id);

        return historicoTransacao.orElse(null);
    }

    public List<HistoricoTransacao> findHistoricoTransacaoByConta(Conta conta) {
        return historicoTransacaoRepository.findHistoricoTransacaoByFk_conta(conta);
    }
}
