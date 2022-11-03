package br.com.projeto.sistemadeavaliacao.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.projeto.sistemadeavaliacao.model.Resposta;

public interface RespostaRepository extends PagingAndSortingRepository<Resposta, Long>{
    
    @Query("SELECT r.comentarioGeral FROM Resposta r")
    public Resposta comentarioGeral();
}
