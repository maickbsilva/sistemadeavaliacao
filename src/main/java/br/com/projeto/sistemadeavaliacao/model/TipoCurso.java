package br.com.projeto.sistemadeavaliacao.model;

public enum TipoCurso {

	FIC("Fic"), CURSO_REGULARES("Curso_Regulares");

	String TipoCr;
	TipoCurso(String string){
		this.TipoCr = string;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
}
