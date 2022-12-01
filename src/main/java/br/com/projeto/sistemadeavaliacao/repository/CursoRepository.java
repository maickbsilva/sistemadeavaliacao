package br.com.projeto.sistemadeavaliacao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import br.com.projeto.sistemadeavaliacao.model.Curso;

public interface CursoRepository extends PagingAndSortingRepository<Curso, Long>{
	@Query("SELECT b FROM Curso b WHERE b.descCurso LIKE %:b%")
	public List<Curso> buscarCurso(@Param("b") String geral);
	
}
