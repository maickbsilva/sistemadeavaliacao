package br.com.projeto.sistemadeavaliacao.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class ItemResposta {
    @Id
    private long id;
    private Pergunta perguntaId;
    private Resposta respostaId;
    private String comentario;
    private int nivelImportancia;
    private int Satisfacao;

}
