package br.com.projeto.sistemadeavaliacao.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Resposta {
    @Id
    private long id;
    private Pesquisa pesquisaId;
    private Date dataRealizacao;
    private String ip;
    private String nomeMaquina;
    private String comentarioGeral;
}
