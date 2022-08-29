package br.com.projeto.sistemadeavaliacao.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class Resposta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Pesquisa pesquisa;
    private Date dataRealizacao;
    private String ip;
    private String nomeMaquina;
    private String comentarioGeral;
    @ManyToOne
    private ItemResposta itemResposta;
}
