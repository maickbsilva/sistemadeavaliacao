package br.com.projeto.sistemadeavaliacao.repository;

import br.com.projeto.sistemadeavaliacao.model.Resposta;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.projeto.sistemadeavaliacao.model.ItemResposta;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ItemRespostaRepository extends PagingAndSortingRepository<ItemResposta, Long>{
    //soma as satisfacoes.
    @Query("SELECT SUM(i.satisfacao) FROM ItemResposta i where i.resposta = :id")
    public String totalSatisfacaoPorResposta(@Param("id") Optional<Resposta> idResposta);
}
