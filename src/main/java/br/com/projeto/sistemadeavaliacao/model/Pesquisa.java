package br.com.projeto.sistemadeavaliacao.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Pesquisa {
    @Id
    private long id;
    //private curso;
    private String turma;
    private Date dataVencimento;
    private List<Resposta> listaResposta;
    private boolean justificativa;
    //private list<doc>
}
