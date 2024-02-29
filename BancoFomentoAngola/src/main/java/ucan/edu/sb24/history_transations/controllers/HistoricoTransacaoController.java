package ucan.edu.sb24.history_transations.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ucan.edu.sb24.ententies.Conta;
import ucan.edu.sb24.history_transations.entities.HistoricoTransacao;
import ucan.edu.sb24.history_transations.repositories.HistoricoTransacaoRepository;
import ucan.edu.sb24.history_transations.services.HistoricoTransacaoService;
import ucan.edu.sb24.history_transations.services.HistoricoTransacaoServiceImpl;
import ucan.edu.sb24.serviceImpl.ContaServiceImpl;


import java.util.List;

@RestController
@RequestMapping("/history")
public class HistoricoTransacaoController {

    @Autowired
    private HistoricoTransacaoServiceImpl historicoTransacaoService;

    @Autowired
    private HistoricoTransacaoRepository historicoTransacaoRepository;

    @Autowired
    private ContaServiceImpl contaService;

    @GetMapping("/findAll")
    public List<HistoricoTransacao> listarTransacoes()
    {
        return historicoTransacaoService.getAllHistTransacoes();

    }
    @GetMapping("/find/{id}")
    public HistoricoTransacao findIdHist(@PathVariable Integer id)
    {
        return historicoTransacaoService.getHistoricoTransacaoById(id);
    }
    @GetMapping("/findByConta/{contaId}")
    public ResponseEntity<List<HistoricoTransacao>> findHistoricoTransacaoByConta(@PathVariable Integer contaId) {
        // Assuming you have a service method that retrieves Conta by ID
        Conta conta = contaService.getContaById(contaId);
        // Retrieve or create Conta instance based on contaId
        List<HistoricoTransacao> historicoTransacao = historicoTransacaoRepository.findHistoricoTransacaoByFk_conta(conta);
        // List<HistoricoTransacao> historicoTransacoes = historicoTransacaoService.findHistoricoTransacaoByConta(conta);

        return ResponseEntity.ok(historicoTransacao);
    }
}
