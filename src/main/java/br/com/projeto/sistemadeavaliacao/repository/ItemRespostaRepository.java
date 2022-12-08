package br.com.projeto.sistemadeavaliacao.repository;

import br.com.projeto.sistemadeavaliacao.model.Pergunta;
import br.com.projeto.sistemadeavaliacao.model.Resposta;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.projeto.sistemadeavaliacao.model.ItemResposta;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ItemRespostaRepository extends PagingAndSortingRepository<ItemResposta, Long> {
    //soma as satisfacoes.
    @Query("SELECT SUM(i.satisfacao) FROM ItemResposta i where i.resposta = :id")
    public String totalSatisfacaoPorResposta(@Param("id") Optional<Resposta> idResposta);

    @Query("SELECT i.pergunta FROM ItemResposta i where i.id = :id")
    public Pergunta findPergunta(@Param("id") Long idItem);

    @Query("SELECT ir.resposta FROM ItemResposta ir WHERE ir.id = :i")
    Resposta buscaResposta(@Param("i") Long idItem);

    @Query("SELECT ir.pendente FROM ItemResposta ir WHERE ir.resposta.id = :r")
    List<Boolean> buscaPendencias(@Param("r") Long idResp);
}
