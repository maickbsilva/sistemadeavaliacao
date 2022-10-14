package br.com.projeto.sistemadeavaliacao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.projeto.sistemadeavaliacao.model.Pergunta;
import br.com.projeto.sistemadeavaliacao.model.Pesquisa;

public interface PesquisaRepository extends CrudRepository<Pesquisa, Long>{

    //@Query("SELECT p.perguntaExclusao FROM Pesquisa p LEFT JOIN p.perguntaExclusao pe where pe.id = :id")

    @Query("SELECT p FROM Pesquisa p INNER JOIN p.perguntaExclusao pe where p.id = :id")
    public List<Pergunta> listaExclusao(@Param("id") Long idPesquisa);
    
}
