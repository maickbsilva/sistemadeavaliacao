package br.com.projeto.sistemadeavaliacao.repository;

import br.com.projeto.sistemadeavaliacao.model.Pesquisa;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.projeto.sistemadeavaliacao.model.Resposta;
import org.springframework.data.repository.query.Param;

public interface RespostaRepository extends PagingAndSortingRepository<Resposta, Long>{

}
