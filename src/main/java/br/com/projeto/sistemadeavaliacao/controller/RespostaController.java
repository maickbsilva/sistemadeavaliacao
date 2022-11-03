package br.com.projeto.sistemadeavaliacao.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.jasypt.util.text.BasicTextEncryptor;
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
        List<Pergunta> perguntas = perguntaRepository.filtroPerguntas(idPesquisa);
        Pesquisa pesquisa = pesquisaRepository.filtroExclusao(idPesquisa);

        // se existir pergunta a se excluida, exclui da lista
        if (pesquisa != null) {
            pesquisa.getPerguntaExclusao().forEach(iten -> {
                perguntas.remove(iten);
            });
        }

        // pega o tamanho da lista
        int numPerg = perguntas.size();

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
            ir.setResposta(resposta);
            ir.setPergunta(perguntas.get(i));

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
        Pesquisa pesquisa = pesquisaRepository.filtroExclusao(id);

        // se existir pergunta a se excluida, exclui da lista
        if (pesquisa != null) {
            pesquisa.getPerguntaExclusao().forEach(iten -> {
                perguntas.remove(iten);
            });
        }

        // outra forma de fazer o for
        // perguntas.stream().filter(iten -> {
        // return !pesquisa.getPerguntaExclusao().contains(iten);
        // });

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
