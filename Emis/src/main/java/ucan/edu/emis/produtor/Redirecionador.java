/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ucan.edu.emis.produtor;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ucan.edu.emis.dto.TransferenciaRequest;

/**
 *
 * @author jfk
 */
@Service
public class Redirecionador {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    private final String key = "0123456789abcdef";

    private static String encryptMessage(String key, String message) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(message.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public String sendMessage(TransferenciaRequest transferenciaRequest, String topico) throws JsonProcessingException {
        try {
            String encryptedMessage = encryptMessage(key, transferenciaRequest.toString());
            kafkaTemplate.send(topico, encryptedMessage);
            System.out.println("\n\n\n\n " + topico + "\n\n\n\n");
            return "Redirecionamento feito com sucesso";
        } catch (Exception ex) {
            System.out.println("202-REDIRECT");
            return "ERRO DE ENCRIPTACAO - Redirecionador";
        }
    }
}
