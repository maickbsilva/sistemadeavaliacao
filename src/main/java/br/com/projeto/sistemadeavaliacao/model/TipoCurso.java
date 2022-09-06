package br.com.projeto.sistemadeavaliacao.model;

import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;


public enum TipoCurso {

	FIC("Fic"), CURSO_REGUlARES("Curso_Regulares");
	
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
