package br.com.projeto.sistemadeavaliacao.model;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

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
    @Column(columnDefinition = "TEXT")
    private String comentarioGeral;
    @OneToMany(mappedBy = "resposta")
    private List<ItemResposta> listaItem;
    private boolean pendente = false;
}
