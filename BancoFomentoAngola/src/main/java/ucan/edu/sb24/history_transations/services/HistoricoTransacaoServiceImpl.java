package ucan.edu.sb24.history_transations.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ucan.edu.sb24.ententies.Conta;
import ucan.edu.sb24.history_transations.entities.HistoricoTransacao;
import ucan.edu.sb24.history_transations.repositories.HistoricoTransacaoRepository;
import ucan.edu.sb24.serviceImpl.ContaServiceImpl;

import java.util.List;
import java.util.Optional;

@Service
public class HistoricoTransacaoServiceImpl implements HistoricoTransacaoService{

    @Autowired
    private ContaServiceImpl contaService;

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

  /*  @Override
    public List<HistoricoTransacao> getAllHistoricoTransacaoByContas( Integer conta) {
        return historicoTransacaoRepository.findHistoricoTransacaoByFk_conta(conta);
    }*/

    public List<HistoricoTransacao> findHistoricoTransacaoByConta(Conta conta) {
        return historicoTransacaoRepository.findHistoricoTransacaoByFk_conta(conta);
    }


}
