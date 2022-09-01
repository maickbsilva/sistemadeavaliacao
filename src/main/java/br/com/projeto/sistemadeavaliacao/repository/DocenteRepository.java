package br.com.projeto.sistemadeavaliacao.repository;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.projeto.sistemadeavaliacao.model.Docente;

public interface DocenteRepository extends PagingAndSortingRepository<Docente, Long>{
}
