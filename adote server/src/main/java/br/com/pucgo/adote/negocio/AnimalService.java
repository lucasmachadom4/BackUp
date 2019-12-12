package br.com.pucgo.adote.negocio;

import java.util.ArrayList;

import br.com.pucgo.adote.entidade.Animal;
import br.com.pucgo.adote.entidade.Tipo;
import br.com.pucgo.adote.entidade.Usuario;
import br.com.pucgo.adote.persistencia.AnimalDAO;
import br.com.pucgo.adote.util.Validar;

public class AnimalService {

	private Validar validar = new Validar();	

	public Animal consultarUltimoAnimalUsuario(int idUsuario) {
		AnimalDAO animalDAO = new AnimalDAO();
		return animalDAO.consultarUltimoAnimalUsuario(idUsuario);
	}	

	public Animal consultarAnimalId(int id) {
		AnimalDAO animalDAO = new AnimalDAO();
		return animalDAO.consultarAnimaiId(id);
	}

	public boolean incluirAnimal(String nome, String descricao, String sexo, String dataNascimento, String cidade,
			String caminhoFoto, double valorDoacao, int idTipo, int idUsuario) {

		if (validar.validaNome(nome) && validar.validaDataNascimento(dataNascimento) && validar.validaNome(cidade)) {
			Animal animal = new Animal();

			animal.setNome(nome);
			animal.setDescricao(descricao.replace("-", ""));
			animal.setSexo(sexo);
			animal.setDataNascimento(validar.formataDataBanco(dataNascimento));
			animal.setCidade(cidade);
			animal.setCaminhoFoto(caminhoFoto);
			animal.setValorDoacao(valorDoacao);
			Tipo tipo = new Tipo(idTipo);
			animal.setTipo(tipo);
			Usuario usuario = new Usuario(idUsuario);
			animal.setUsuario(usuario);

			AnimalDAO animalDAO = new AnimalDAO(); //
			return animalDAO.incluirAnimal(animal);
		} else {
			return false;
		}
	}

	public boolean alterarAnimal(int id, String nome, String descricao, String sexo, String dataNascimento, String cidade,
			String caminhoFoto, double valorDoacao, int idTipo) {

		if (validar.validaNome(nome) && validar.validaDataNascimento(dataNascimento) && validar.validaNome(cidade)) {
			Animal animal = new Animal();
			animal.setId(id);
			animal.setNome(nome);
			animal.setDescricao(descricao);
			animal.setSexo(sexo);
			animal.setDataNascimento(validar.formataDataBanco(dataNascimento));
			animal.setCidade(cidade);
			animal.setCaminhoFoto(caminhoFoto);
			animal.setValorDoacao(valorDoacao);
			Tipo tipo = new Tipo(idTipo);
			animal.setTipo(tipo);
			
			AnimalDAO animalDAO = new AnimalDAO(); //
			return animalDAO.alterarAnimal(animal);
		} else {
			return false;
		}
	}

	public boolean excluirAnimal(int id) {
		AnimalDAO animalDAO = new AnimalDAO();
		return animalDAO.excluirAnimal(id);
	}
	
	public ArrayList<Animal> consultarAnimaisUsuario(int id) {
		AnimalDAO animalDAO = new AnimalDAO();
		return animalDAO.consultarAnimaisUsuario(id);
	}	
	
	public ArrayList<Animal> consultarAnimaisPor(String pesquisa) {
		AnimalDAO animalDAO = new AnimalDAO();
		return animalDAO.consultarAnimaisPor(pesquisa);
	}

}
