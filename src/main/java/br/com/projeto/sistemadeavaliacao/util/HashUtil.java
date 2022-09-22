package br.com.projeto.sistemadeavaliacao.util;

import java.nio.charset.StandardCharsets;

import com.google.common.hash.Hashing;

/**
 * Classe responsável pela criptografia das senhas.
 * 
 * @author Projeto Sistema de Avaliação
 *
 */

public class HashUtil {

	public static String hash(String palavra) {
		String salt = "B@By";
		palavra = salt + palavra;
		String hash = Hashing.sha256().hashString(palavra, StandardCharsets.UTF_8).toString();
		return hash;
	}

}
