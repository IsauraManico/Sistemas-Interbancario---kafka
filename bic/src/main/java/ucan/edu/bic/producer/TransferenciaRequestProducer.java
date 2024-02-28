/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ucan.edu.bic.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import ucan.edu.bic.dto.TransferenciaRequest;

/**
 *
 * @author jfk
 */
@Service
public class TransferenciaRequestProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Value("${kafka.topic.intra}")
    private String transferenciaIntraTopic;
    @Value("${kafka.topic.inter}")
    private String transferenciaInterTopic;
    @Value("${kafka.topic.error}")
    private String errorTopic;
    private final String key = "0123456789abcdef";

    private static String encryptMessage(String key, String message) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(message.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public String sendMessageIntra(TransferenciaRequest transferenciaRequest) throws JsonProcessingException {

        try {
            String encryptedMessage = encryptMessage(key, transferenciaRequest.toString());
            kafkaTemplate.send(transferenciaIntraTopic, encryptedMessage);
            return "Transferencia INTRA enviada para processamento";
        } catch (Exception ex) {
            return "ERRO DE ENCRIPTACAO - TransferenciaRequestProducer.Intra";
        }

    }

    public String sendMessageInter(TransferenciaRequest transferenciaRequest) throws JsonProcessingException {

        try {
            String encryptedMessage = encryptMessage(key, transferenciaRequest.toString());
            kafkaTemplate.send(transferenciaInterTopic, encryptedMessage);
            return "Transferencia INTER enviada para processamento";
        } catch (Exception ex) {
            return "ERRO DE ENCRIPTACAO - TransferenciaRequestProducer.Inter";
        }
    }

    public String sendMessageError404(TransferenciaRequest transferenciaRequest) throws JsonProcessingException {

        try {
            String encryptedMessage = encryptMessage(key, transferenciaRequest.toString());
            kafkaTemplate.send(errorTopic, encryptedMessage);
            return "CONTA NOT FOUND - 404";
        } catch (Exception ex) {
            return "ERRO DE ENCRIPTACAO - TransferenciaRequestProducer.Error";
        }

    }
}
