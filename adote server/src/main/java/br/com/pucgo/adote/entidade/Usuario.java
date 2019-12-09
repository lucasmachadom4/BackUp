package br.com.pucgo.adote.entidade;

public class Usuario {
	
	private int id;
	private String nome;
	private String email;
	private String senha;
	private String telefone1;
	private String telefone2;	
	
	/**	 
	 * @param id
	 * @param nome
	 * @param email
	 * @param senha
	 * @param telefone1
	 * @param telefone2
	 */
	public Usuario(int id) {
		this.id = id;		
	}

	public Usuario() {		
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone1() {
		return telefone1;
	}

	public void setTelefone1(String telefone1) {
		this.telefone1 = telefone1;
	}

	public String getTelefone2() {
		return telefone2;
	}

	public void setTelefone2(String telefone2) {
		this.telefone2 = telefone2;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
