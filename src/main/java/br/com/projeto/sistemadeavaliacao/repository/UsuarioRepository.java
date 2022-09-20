package br.com.projeto.sistemadeavaliacao.repository;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.projeto.sistemadeavaliacao.model.Usuario;

/**
 * Classe responsável por relizar a persistência do sistema.
 * 
 * @author Projeto Sistema de Avaliação
 *
 */

@Repository
public interface UsuarioRepository extends PagingAndSortingRepository<Usuario, Long> {

	
	public Usuario findByNifAndSenha(String nif, String senha);
	
	//@Query("SELECT c FROM Usuario c WHERE c.nome LIKE %:t% OR c.nif LIKE %:t% OR c.tipo LIKE %:t% ")
	//public List<Usuario> Buscar(@Param("t") String tudo);
	

}