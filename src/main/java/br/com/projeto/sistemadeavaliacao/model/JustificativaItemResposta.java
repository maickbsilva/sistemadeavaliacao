package br.com.projeto.sistemadeavaliacao.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class JustificativaItemResposta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Usuario usuario;
    private String texto;
    private Date data;
    @OneToOne
    private ItemResposta itemResposta;
}
