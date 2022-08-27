package br.com.projeto.sistemadeavaliacao.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class Pesquisa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Curso curso;
    private String turma;
    private Date dataVencimento;
    @OneToMany(mappedBy = "pesquisa")
    private List<Resposta> listaResposta;
    private boolean justificativa;
    
    //private list<doc>
}
