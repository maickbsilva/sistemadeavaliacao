package br.com.projeto.sistemadeavaliacao.repository;

import br.com.projeto.sistemadeavaliacao.model.JustificativaItemResposta;
import br.com.projeto.sistemadeavaliacao.model.JustificativaResposta;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface JustificativaRespostaRepository extends CrudRepository<JustificativaResposta, Long> {
    List<JustificativaResposta> findAllByOrderByIdDesc();

}
