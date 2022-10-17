package br.com.projeto.sistemadeavaliacao.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

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

    @OneToOne
    private Usuario usuarioDocente;

    @ManyToMany
    private List<Usuario> listaDocentes;

    @ManyToMany
    private List<Pergunta> perguntaExclusao;

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Pesquisa other = (Pesquisa) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

       
    
}
