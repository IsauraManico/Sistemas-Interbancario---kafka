package ucan.edu.bic.dto;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 *
 * @author jfk
 */
public class Encriptacao {

    private final String key = "0123456789abcdef"; // Chave de 128 bits (16 caracteres hexadecimais)

    public static String encryptMessage(String key, String message) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(message.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decryptMessage(String key, String encryptedMessage) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedMessage);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }/*

    public static void main(String[] args) {

        TransferenciaRequest transferencia = new TransferenciaRequest();
        transferencia.setPk(1);
        transferencia.setPk2(2);
        transferencia.setEncargos(299);
        transferencia.setMontante(900000);
        transferencia.setStatus(3);
        transferencia.setContaDestinoIban("AO06004000023243");
        transferencia.setContaOrigemIban("AO0600400002318");

        try {
            String code = transferencia.toString();
            String key = "0123456789abcdef"; // Chave de 128 bits (16 caracteres hexadecimais)
            String encryptedMessage = encryptMessage(key, code);
            System.out.println(encryptedMessage + "- " + transferencia.toString());

            System.out.println(decryptMessage(key, encryptedMessage) + transferencia.fromString(decryptMessage(key, encryptedMessage)));

        } catch (Exception ex) {
            Logger.getLogger(Encriptacao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }*/
}
