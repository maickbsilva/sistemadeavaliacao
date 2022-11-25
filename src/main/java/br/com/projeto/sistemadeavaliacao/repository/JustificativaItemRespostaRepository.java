package br.com.projeto.sistemadeavaliacao.repository;

import br.com.projeto.sistemadeavaliacao.model.ItemResposta;
import br.com.projeto.sistemadeavaliacao.model.JustificativaItemResposta;
import br.com.projeto.sistemadeavaliacao.model.Resposta;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JustificativaItemRespostaRepository extends CrudRepository<JustificativaItemResposta, Long> {
    List<JustificativaItemResposta> findAllByOrderByIdDesc();
}
