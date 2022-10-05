package br.com.projeto.sistemadeavaliacao.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import br.com.projeto.sistemadeavaliacao.model.Resposta;
import br.com.projeto.sistemadeavaliacao.repository.RespostaRepository;

@RestController
@RequestMapping("/api/resposta")
public class RespostaRestController {
	
	@Autowired
	private RespostaRepository respository;

	//Listar
	
	@RequestMapping(value = "listar", method = RequestMethod.GET)
	public Iterable<Resposta> getResposta(){
		
		return respository.findAll();	
		
}
	
	public ResponseEntity<Resposta> getRespota(@PathVariable("/{id}") Long idResposta){
		
		java.util.Optional<Resposta> optional = respository.findById(idResposta);
		if(optional.isPresent()) {
			return ResponseEntity.ok(optional.get());
		}else {
			return ResponseEntity.notFound().build();
		}
		
	}

}
