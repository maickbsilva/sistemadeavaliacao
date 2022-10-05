package br.com.projeto.sistemadeavaliacao.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.projeto.sistemadeavaliacao.model.Pesquisa;

public interface PesquisaRepository extends CrudRepository<Pesquisa, Long>{

    /*@Query("SELECT p FROM Pergunta p LEFT JOIN p.listaPesquisa pe where size(p.listaPesquisa) = 0 OR pe.id = :id")
    public List<Pergunta> filtroPerguntas(@Param("id") Long idPesquisa); */
    
}
