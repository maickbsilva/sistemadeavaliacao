package br.com.projeto.sistemadeavaliacao.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;
import org.jasypt.util.text.BasicTextEncryptor;

@Entity
@Data
public class ItemResposta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Resposta resposta;
    private String comentario;
    private String nivelImportancia;
    private String satisfacao;
    @ManyToOne
    private Pergunta pergunta;
    public void setComentario(String comentario) {
        BasicTextEncryptor encryptor = new BasicTextEncryptor();
        encryptor.setPasswordCharArray("crypto".toCharArray());
        this.comentario = encryptor.encrypt(comentario);
    }
    public String getComentario() {
        BasicTextEncryptor encryptor = new BasicTextEncryptor();
        encryptor.setPasswordCharArray("crypto".toCharArray());
        return encryptor.decrypt(comentario);
    }
}
