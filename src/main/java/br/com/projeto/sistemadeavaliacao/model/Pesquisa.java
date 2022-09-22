package br.com.projeto.sistemadeavaliacao.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.format.annotation.DateTimeFormat;

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
    //enum do periodo
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(columnDefinition = "DATE")
    private Date dataVencimento;
    
    @OneToMany(mappedBy = "pesquisa")
    private List<Resposta> listaResposta;
    
    //lista de ITEMs
    @OneToMany(mappedBy = "pesquisa")
    private List<ItemResposta> listaItem;
    
    private boolean justificativa;
    
    
}
