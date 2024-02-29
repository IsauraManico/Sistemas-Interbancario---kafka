/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ucan.edu.emis.consumidor;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ucan.edu.emis.dto.StatusENUM;
import ucan.edu.emis.dto.TransferenciaRequest;
import ucan.edu.emis.entidade.Status;
import ucan.edu.emis.entidade.Transacao;
import ucan.edu.emis.produtor.Redirecionador;
import ucan.edu.emis.service.StatusService;
import ucan.edu.emis.service.TransacaoService;

/**
 *
 * @author jfk
 */
@Service
public class ErrorTransferenciaConsumer {

    public ErrorTransferenciaConsumer(ucan.edu.emis.service.TransacaoService transacaoService) {
        this.transacaoService = transacaoService;

    }
    @Autowired
    private Redirecionador redirecionador;
    private final String key = "0123456789abcdef";
    private final TransacaoService transacaoService;

    @KafkaListener(
            topics = "${kafka.topic.erro}",
            groupId = "EMIS"
    )
    public void consume(String message) {

        try {
            TransferenciaRequest transferencia;
            transferencia = TransferenciaRequest.fromString(decryptMessage(key, message));
            StatusENUM resultado = processarRetorno(transferencia);
            System.out.println("===== RETORNO PROCESSADO ===> " + resultado);
        } catch (Exception ex) {
            System.out.println("ERRO NA ENCRIPTACAO - ERROR" + ex.getMessage());
        }
    }
// a emis redireciona para um banco especifico apartir do id do banco
    private StatusENUM processarRetorno(TransferenciaRequest transferencia) {

        Integer numBanco = Integer.parseInt(extractDigitsAfterAO06(transferencia.getContaOrigemIban()));
        String topicRoute = "EMIS-" + numBanco;
        Transacao trancacao = transferencia.getTransacao();
        trancacao.setDataFinal(new Date());
        trancacao.setFkStatus(3);
        transacaoService.updateTransacao(trancacao.getPkTransacao(), trancacao);

        try {
            redirecionador.sendMessage(transferencia, topicRoute);
        } catch (JsonProcessingException ex) {
            System.err.println("Erro ao enviar mensagem de erro" + ex.getMessage());
            return StatusENUM.REJEITADA;
        }
        return StatusENUM.CONCLUIDA;
    }

    private static String extractDigitsAfterAO06(String iban) {
        Pattern pattern = Pattern.compile("AO06(\\d{4})");
        Matcher matcher = pattern.matcher(iban);

        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return "Não foi possível encontrar os 4 dígitos após 'AO06'\n" + iban;
        }
    }

    private static String decryptMessage(String key, String encryptedMessage) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedMessage);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }
}
