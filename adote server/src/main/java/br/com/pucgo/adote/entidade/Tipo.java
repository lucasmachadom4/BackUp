package br.com.pucgo.adote.entidade;

public class Tipo {
	
	private int id;
	private String nome;
	
	/**
	 * @param id
	 * @param nome
	 */
	public Tipo(int id) {		
		this.id = id;	
	}
	
	public Tipo() {		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
}
