package br.com.projeto.sistemadeavaliacao.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.projeto.sistemadeavaliacao.model.Resposta;

public interface RespostaRepository extends PagingAndSortingRepository<Resposta, Long>{
    
}
