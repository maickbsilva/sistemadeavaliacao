package br.com.projeto.sistemadeavaliacao.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Justificativa {
    @Id
    private long id;
    //private usuarioid;
    private ItemResposta itemRespostaId;
    private String texto;
}
