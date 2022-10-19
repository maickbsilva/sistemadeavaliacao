package br.com.projeto.sistemadeavaliacao.model;

import javax.persistence.*;

import lombok.Data;

import java.util.Date;

@Entity
@Data
public class JustificativaResposta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Usuario usuario;
    private String texto;
    private Date data;
    @OneToOne
    private Resposta resposta;
}
