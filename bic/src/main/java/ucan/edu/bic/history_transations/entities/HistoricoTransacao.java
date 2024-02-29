package ucan.edu.bic.history_transations.entities;

import jakarta.persistence.*;
import lombok.Data;
import ucan.edu.bic.ententies.Conta;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "historico_transacao")
@Data
public class HistoricoTransacao implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pk_historico_transacao;

    @JoinColumn(name = " fk_conta", referencedColumnName = "pkconta")
    @ManyToOne
    private Conta fk_conta;
    private Date datTransacao;
    private Double saldo;
    private String descricao;






    public Date getDatTransacao() {
        return datTransacao;
    }

    public void setDatTransacao(Date datTransacao) {
        this.datTransacao = datTransacao;
    }


}
