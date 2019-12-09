package br.com.pucgo.adote.entidade;

public class Animal {

	private int id;
	private String nome;
	private String descricao;	
	private String sexo;	
	private String dataNascimento;
	private String cidade;
	private double valorDoacao;
	private String caminhoFoto;	
	
	private Tipo tipo;	
	private Usuario usuario;
	
	/**
	 * 
	 * @param id
	 * @param nome
	 * @param descricao
	 * @param sexo
	 * @param dataNascimento
	 * @param cidade
	 * @param valorDoacao
	 * @param caminhoFoto
	 * @param tipo
	 * @param usuario
	 */
	public Animal(int id, String nome, String descricao, String sexo, String dataNascimento, String cidade,
			double valorDoacao, String caminhoFoto, Tipo tipo, Usuario usuario) {		
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.sexo = sexo;
		this.dataNascimento = dataNascimento;
		this.cidade = cidade;
		this.valorDoacao = valorDoacao;
		this.caminhoFoto = caminhoFoto;
		this.tipo = tipo;
		this.usuario = usuario;
	}
	
	public Animal() {}

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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}	

	public double getValorDoacao() {
		return valorDoacao;
	}

	public void setValorDoacao(double valorDoacao) {
		this.valorDoacao = valorDoacao;
	}

	public String getCaminhoFoto() {
		return caminhoFoto;
	}

	public void setCaminhoFoto(String caminhoFoto) {
		this.caminhoFoto = caminhoFoto;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	
	
}
