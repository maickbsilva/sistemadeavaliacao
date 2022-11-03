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
import org.jasypt.util.text.BasicTextEncryptor;

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

    public void setComentarioGeral(String comentarioGeral) {
        BasicTextEncryptor encryptor = new BasicTextEncryptor();
        encryptor.setPasswordCharArray("crypto".toCharArray());
        this.comentarioGeral = encryptor.encrypt(comentarioGeral);
    }

    public String getComentarioGeral() {
        BasicTextEncryptor encryptor = new BasicTextEncryptor();
        encryptor.setPasswordCharArray("crypto".toCharArray());
        return encryptor.decrypt(comentarioGeral);
    }
}
