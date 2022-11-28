package br.com.projeto.sistemadeavaliacao.repository;

import br.com.projeto.sistemadeavaliacao.model.Pesquisa;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.projeto.sistemadeavaliacao.model.Resposta;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RespostaRepository extends PagingAndSortingRepository<Resposta, Long> {
    @Query("SELECT COUNT(r.id) FROM Resposta r where r.pesquisa.id = :id")
    public Long contaResposta(@Param("id") Long idPesquisa);
    @Query("SELECT r.pesquisa FROM Resposta r WHERE r.id = :i")
    Pesquisa buscaPesquisa(@Param("i") Long idResp);
}
