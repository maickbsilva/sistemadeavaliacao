package br.com.projeto.sistemadeavaliacao.repository;

import br.com.projeto.sistemadeavaliacao.model.Resposta;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.projeto.sistemadeavaliacao.model.ItemResposta;
import org.springframework.data.repository.query.Param;

public interface ItemRespostaRepository extends PagingAndSortingRepository<ItemResposta, Long>{

    @Query("SELECT ir FROM ItemResposta ir where ir.resposta = :id")
    public Resposta satisfacoesPorResposta(@Param("id") Long idResposta);
    
}
