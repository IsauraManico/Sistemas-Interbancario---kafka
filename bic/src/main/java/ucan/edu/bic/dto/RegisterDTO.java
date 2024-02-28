package ucan.edu.bic.dto;



import ucan.edu.bic.enums.ContaRole;

import java.util.Date;

public record RegisterDTO(String nif, String nome, Date data_entidade, String email, String telefone,
                          String morada, ContaRole contaRole, String password) {
}
