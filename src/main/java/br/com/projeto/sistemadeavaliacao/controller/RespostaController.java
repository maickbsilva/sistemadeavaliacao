package br.com.projeto.sistemadeavaliacao.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.projeto.sistemadeavaliacao.annotation.PublicoAnnotation;
import br.com.projeto.sistemadeavaliacao.model.ItemResposta;
import br.com.projeto.sistemadeavaliacao.model.Pergunta;
import br.com.projeto.sistemadeavaliacao.model.Pesquisa;
import br.com.projeto.sistemadeavaliacao.model.Resposta;
import br.com.projeto.sistemadeavaliacao.repository.ItemRespostaRepository;
import br.com.projeto.sistemadeavaliacao.repository.PerguntaRepository;
import br.com.projeto.sistemadeavaliacao.repository.PesquisaRepository;
import br.com.projeto.sistemadeavaliacao.repository.RespostaRepository;

@Controller
@RequestMapping("resposta/")
public class RespostaController {

	@Autowired
	private ItemRespostaRepository itemRespostaRepository;

	@Autowired
	private PesquisaRepository pesquisaRepository;

	@Autowired
	private RespostaRepository respostaRepository;

	@Autowired
	private PerguntaRepository perguntaRepository;

	@PublicoAnnotation
	@RequestMapping(value = "novoFormulario", method = RequestMethod.POST)
	public String novaResposta(Resposta resposta, String nivelImportancia, String satisfacao, String comentario,
			Model model, HttpServletRequest request) throws UnknownHostException {

		// pega id da pesquisa
		Long idPesquisa = resposta.getPesquisa().getId();

		// passa no filtro
		List<Pergunta> listaPerg = (List<Pergunta>) perguntaRepository.filtroPerguntas(idPesquisa);

		//verifica se o id da pergunta tem uma pergunta para excluir
		//se tiver, remove essa pergunta da lista de perguntas
		// List<Pesquisa> listaExclusao = pesquisaRepository.listaExclusao();
		// for (int i = 0; i < listaPerg.size(); i++) {
		// 	//se o id da pergunta for igual o id da lista de exclusao, remove
		// 	if(listaPerg.get(i).equals(listaExclusao.get(i)))
		// }

		// pega o tamanho da lista
		int numPerg = listaPerg.size();

		// pega a data de hoje e seta no atributo
		Date now = new Date(System.currentTimeMillis());
		resposta.setDataRealizacao(now);

		// pega o ip da maquina e seta no atributo
		String ipDaMaquina = InetAddress.getLocalHost().getHostAddress();
		resposta.setIp(ipDaMaquina);

		// pega o nome da maquina e seta no atributo
		InetAddress addr = InetAddress.getLocalHost();
		String hostname = addr.getHostName();
		resposta.setNomeMaquina(hostname);

		// salva a resposta
		respostaRepository.save(resposta);

		// pega as strings e transforma em array
		String[] quebraNivel = nivelImportancia.split(",");
		String[] quebraComent = comentario.split(",");
		String[] quebraSatis = satisfacao.split(",");

		// seta um item resposta para cada pergunta
		for (int i = 0; i < numPerg; i++) {
			ItemResposta ir = new ItemResposta();
			ir.setPesquisa(resposta.getPesquisa());
			ir.setResposta(resposta);
			ir.setPergunta(listaPerg.get(i));

			String nivel = quebraNivel[i];
			String coment = quebraComent[i];
			String satisf = quebraSatis[i];

			ir.setNivelImportancia(nivel);
			ir.setComentario(coment);
			ir.setSatisfacao(satisf);

			itemRespostaRepository.save(ir);
		}

		return "resposta/sucesso";
	}

	@PublicoAnnotation
	@RequestMapping("buscar")
	public String buscaPesquisa(Long id, Model model) {
		List<Pergunta> perguntas = perguntaRepository.filtroPerguntas(id);
		model.addAttribute("pesq", pesquisaRepository.findById(id).get());
		model.addAttribute("perg", perguntas);
		model.addAttribute("item", itemRespostaRepository.findAll());
		return "resposta/formulario";
	}

	@PublicoAnnotation
	@RequestMapping("codigoPesquisa")
	public String codigoPesquisa() {
		return "resposta/codigoPesquisa";
	}
}
