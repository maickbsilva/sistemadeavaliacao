package br.com.projeto.sistemadeavaliacao.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class ItemResposta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //private Pergunta perguntaId;
    //private Resposta respostaId;
    private String comentario;
    private int nivelImportancia;
    private int Satisfacao;
    @ManyToOne
    private Pergunta pergunta;

}
