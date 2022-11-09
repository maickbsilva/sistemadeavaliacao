package br.com.projeto.sistemadeavaliacao.repository;

import br.com.projeto.sistemadeavaliacao.model.Pesquisa;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PesquisaRepository extends CrudRepository<Pesquisa, Long> {
    //busca as perguntas das pesquisas que tem perguntas a serem excluidas
    @Query("SELECT l FROM Pesquisa l INNER JOIN l.perguntaExclusao li where l.id = :id")
    public Pesquisa filtroExclusao(@Param("id") Long idPesquisa);

    @Query("SELECT l FROM Pesquisa l where l.dataVencimento > CURRENT_DATE order by l.id DESC")
    public List<Pesquisa> filtroNaoVencidos();

    @Query("SELECT l FROM Pesquisa l join l.listaDocentes ld where l.usuarioDocente.userId = :user OR ld.userId = :user  order by l.id DESC")
    public List<Pesquisa> listaPesquisaPorDocente(@Param("user") Long user);

}
