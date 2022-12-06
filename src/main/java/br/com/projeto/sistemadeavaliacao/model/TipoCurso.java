package br.com.projeto.sistemadeavaliacao.model;

public enum TipoCurso {

	FIC("FIC"), CURSO_REGULAR("Curso Regular");

	String TipoCr;

	TipoCurso(String string) {
		this.TipoCr = string;
	}

	@Override
	public String toString() {

		return TipoCr;
	}
}
