package br.com.projeto.sistemadeavaliacao.repository;

import br.com.projeto.sistemadeavaliacao.model.Pesquisa;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.projeto.sistemadeavaliacao.model.Resposta;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RespostaRepository extends PagingAndSortingRepository<Resposta, Long>{
//SELECT count(id) FROM sistemadeavaliacao.resposta where resposta.pesquisa_id = 20
@Query("SELECT COUNT(r.id) FROM Resposta r where r.pesquisa.id = :id")
public Long contaResposta(@Param("id") Long idPesquisa);
}
