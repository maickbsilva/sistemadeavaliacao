package br.com.projeto.sistemadeavaliacao.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import br.com.projeto.sistemadeavaliacao.util.HashUtil;
import lombok.Data;

/**
 * Classe responsável pelo gerenciamento da entidade Usuario.
 * 
 * @author Projeto Sistema de Avaliação
 *
 */
@Data
@Entity
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String senha;
	@Column(nullable = false, unique = true)
	@NotEmpty
	private String nif;
	@NotEmpty
	private String nome;
	private TipoUsuario tipo;

	public void setSenha(String senha) {
		this.senha = HashUtil.hash(senha);

	}

}
