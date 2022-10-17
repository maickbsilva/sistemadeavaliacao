package br.com.projeto.sistemadeavaliacao.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import br.com.projeto.sistemadeavaliacao.model.Pergunta;

public interface PerguntaRepository extends PagingAndSortingRepository<Pergunta, Long> {

    //busca as perguntas especificas e as perguntas gerais
    @Query("SELECT p FROM Pergunta p LEFT JOIN p.listaPesquisa pe where size(p.listaPesquisa) = 0 OR pe.id = :id")
    public List<Pergunta> filtroPerguntas(@Param("id") Long idPesquisa);

    //busca somente as perguntas gerais
    @Query("SELECT p FROM Pergunta p LEFT JOIN p.listaPesquisa pe where size(p.listaPesquisa) = 0")
    public List<Pergunta> perguntasGerais();
}
