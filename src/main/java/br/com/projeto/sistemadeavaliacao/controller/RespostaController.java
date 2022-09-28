package br.com.projeto.sistemadeavaliacao.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.projeto.sistemadeavaliacao.annotation.PublicoAnnotation;
import br.com.projeto.sistemadeavaliacao.model.ItemResposta;
import br.com.projeto.sistemadeavaliacao.model.Pergunta;
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
	@RequestMapping("formulario")
	public String formulario(Model model, Pergunta pergunta) {
		model.addAttribute("perg", perguntaRepository.findAll());
		model.addAttribute("item", itemRespostaRepository.findAll());
		model.addAttribute("pesq", pesquisaRepository.findAll());
		return "resposta/formulario";
	}

	@PublicoAnnotation
	@RequestMapping(value = "novoFormulario", method = RequestMethod.POST)
	public String novaResposta(Resposta resposta, String nivelImportancia, String satisfacao, String comentario,
			Model model, HttpServletRequest request) throws UnknownHostException {

		List<Pergunta> listaPerg = (List<Pergunta>) perguntaRepository.findAll();
		int numPerg = listaPerg.size();

		Date now = new Date(System.currentTimeMillis());
		resposta.setDataRealizacao(now);

		String ipDaMaquina = InetAddress.getLocalHost().getHostAddress();
		resposta.setIp(ipDaMaquina);

		InetAddress addr = InetAddress.getLocalHost();
		String hostname = addr.getHostName();
		resposta.setNomeMaquina(hostname);

		respostaRepository.save(resposta);

		String pegaNivel = nivelImportancia;
		String[] quebraNivel = pegaNivel.split(",");

		String pegaComent = comentario;
		String[] quebraComent = pegaComent.split(",");

		String pegaSatis = satisfacao;
		String[] quebraSatis = pegaSatis.split(",");
                

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
		model.addAttribute("perg", perguntaRepository.findAll());
		model.addAttribute("item", itemRespostaRepository.findAll());
		model.addAttribute("pesq", pesquisaRepository.findById(id).get());
		return "resposta/formulario";
	}

	@PublicoAnnotation
	@RequestMapping("codigoPesquisa")
	public String codigoPesquisa() {
		return "resposta/codigoPesquisa";
	}
}
