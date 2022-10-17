package br.com.projeto.sistemadeavaliacao.repository;

import br.com.projeto.sistemadeavaliacao.model.Pesquisa;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface PesquisaRepository extends CrudRepository<Pesquisa, Long> {
    @Query("SELECT l FROM Pesquisa l INNER JOIN l.perguntaExclusao li where l.id = :id")
    public Pesquisa filtroExclusao(@Param("id") Long idPesquisa);

}
