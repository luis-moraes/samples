package br.com.luisca.sample.domain;

import java.util.List;

public class Pagina {

	private List<Sample> dados;
	
	public Pagina(){ }
	
	public Pagina(List<Sample> dados){
		this.dados = dados;
	}

	public List<Sample> getDados() {
		return dados;
	}

	public void setDados(List<Sample> dados) {
		this.dados = dados;
	}
}
