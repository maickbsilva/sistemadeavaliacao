package br.com.projeto.sistemadeavaliacao.model;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

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
    
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Calendar dataVencimento;
    
    @OneToMany(mappedBy = "pesquisa")
    private List<Resposta> listaResposta;
    private boolean justificativa;
    @OneToMany
    private List<Docente> listaDocente;
    
}
