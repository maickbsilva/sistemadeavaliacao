package br.com.projeto.sistemadeavaliacao.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.projeto.sistemadeavaliacao.model.Pesquisa;

public interface PesquisaRepository extends PagingAndSortingRepository<Pesquisa, Long>{

}
