package br.com.projeto.sistemadeavaliacao.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.projeto.sistemadeavaliacao.model.Curso;

public interface CursoRepository extends PagingAndSortingRepository<Curso, Long>{
    
}
