package br.com.projeto.sistemadeavaliacao.model;

import javax.persistence.*;

import lombok.Data;

@Entity
@Data
public class ItemResposta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Resposta resposta;
    @Column(columnDefinition = "TEXT")
    private String comentario;
    private String nivelImportancia;
    private String satisfacao;
    @ManyToOne
    private Pergunta pergunta;
    private boolean pendente = false;
}
