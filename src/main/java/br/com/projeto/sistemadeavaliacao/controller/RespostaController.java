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

    // convoca o formulario
    @RequestMapping("formulario")
    public String formulario(Model model, Pergunta pergunta) {
        model.addAttribute("perg", perguntaRepository.findAll());
        model.addAttribute("item", itemRespostaRepository.findAll());
        model.addAttribute("pesq", pesquisaRepository.findAll());

        return "resposta/formulario";
    }

    // insere um novo formulario preechido
    @RequestMapping(value = "novoFormulario", method = RequestMethod.POST)
    public String novaResposta(Resposta resposta, String nivelImportancia, String satisfacao, 
            String comentario, Model model, 
            HttpServletRequest request) throws UnknownHostException {

        // pega a lista de perguntas e seu tamanho
        List<Pergunta> listaPerg = (List<Pergunta>) perguntaRepository.findAll();
        int numPerg = listaPerg.size();

        // pega a data atual e seta na variavel dataRealizacao
        Date now = new Date(System.currentTimeMillis());
        resposta.setDataRealizacao(now);

        // pega o ip da maquina
        String ipDaMaquina = InetAddress.getLocalHost().getHostAddress();
        resposta.setIp(ipDaMaquina);

        // pega o nome da maquina
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

    @RequestMapping("buscar")
    public String buscaPesquisa(Long id, Model model) {
        model.addAttribute("perg", perguntaRepository.findAll());
        model.addAttribute("item", itemRespostaRepository.findAll());
        model.addAttribute("pesq", pesquisaRepository.findById(id).get());
        return "resposta/formulario";
    }

    @RequestMapping("codigoPesquisa")
    public String codigoPesquisa() {
        return "resposta/codigoPesquisa";
    }
}
