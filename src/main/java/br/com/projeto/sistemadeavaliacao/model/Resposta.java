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
    @OneToMany(mappedBy = "resposta")
    private List<ItemResposta> listaItem;
}
