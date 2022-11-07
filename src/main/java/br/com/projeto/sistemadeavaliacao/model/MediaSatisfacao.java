package br.com.projeto.sistemadeavaliacao.model;

import lombok.Data;

import java.util.List;

@Data
public abstract class MediaSatisfacao {

    private List<Pergunta> perguntas;

    private List<ItemResposta> satisfacoes;

    private double media;

    private double soma;
}
