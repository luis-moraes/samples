package br.com.luisca.sample.domain;

public class Sample {
	
	private long id;
	private String nome;
	
	public Sample(){};
	
	public Sample(long id,String nome){
		this.id = id;
		this.nome = nome;
	}
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	

}