package br.com.projeto.sistemadeavaliacao.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import lombok.Data;

@Entity
@Data
public class Pergunta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String textoPergunta;
    @ManyToMany
    private List<Pesquisa> listaPesquisa;

}
