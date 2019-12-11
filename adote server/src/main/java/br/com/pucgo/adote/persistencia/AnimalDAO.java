package br.com.pucgo.adote.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import br.com.pucgo.adote.entidade.Animal;
import br.com.pucgo.adote.entidade.Usuario;

public class AnimalDAO {

	private Conecxao conexaoDB = new Conecxao();

	public ArrayList<Animal> consultarAnimais() {
		try {
			ArrayList<Animal> listaAnimais = new ArrayList<Animal>();
			Connection conn = conexaoDB.abreConexao();
			PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM animal;");
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Animal animal = new Animal();

				animal.setId(resultSet.getInt("id"));
				animal.setNome(resultSet.getString("nome"));
				animal.setDescricao(resultSet.getString("descricao"));
				animal.setSexo(resultSet.getString("sexo"));
				animal.setDataNascimento(resultSet.getString("datanascimento"));
				animal.setCidade(resultSet.getString("cidade"));
				animal.setValorDoacao(resultSet.getDouble("valordoacao"));
				animal.setCaminhoFoto(resultSet.getString("imagem"));
				TipoDAO tipoDAO = new TipoDAO();
				animal.setTipo(tipoDAO.consultaTipoId(resultSet.getInt("tipo_id")));
				UsuarioDAO usuarioDAO = new UsuarioDAO();
				animal.setUsuario(usuarioDAO.consultarUsuarioId(resultSet.getInt("usuario_id")));
				listaAnimais.add(animal);
			}
			conn.close();
			return listaAnimais;
		} catch (Exception e) {
			System.out.println("Erro na consulta: " + e.getMessage());			
			return null;
		}
	}

	public Animal consultarAnimaiId(int id) {
		try {
			Animal animal = new Animal();
			Connection conn = conexaoDB.abreConexao();
			PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM animal where id = " + id + ";");
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {

				animal.setId(resultSet.getInt("id"));
				animal.setNome(resultSet.getString("nome"));
				animal.setDescricao(resultSet.getString("descricao"));
				animal.setSexo(resultSet.getString("sexo"));
				animal.setDataNascimento(resultSet.getString("datanascimento"));
				animal.setCidade(resultSet.getString("cidade"));
				animal.setValorDoacao(resultSet.getDouble("valordoacao"));
				animal.setCaminhoFoto(resultSet.getString("imagem"));
				TipoDAO tipoDAO = new TipoDAO();
				animal.setTipo(tipoDAO.consultaTipoId(resultSet.getInt("tipo_id")));
				UsuarioDAO usuarioDAO = new UsuarioDAO();
				animal.setUsuario(usuarioDAO.consultarUsuarioId(resultSet.getInt("usuario_id")));
			}
			conn.close();
			return animal;
		} catch (Exception e) {
			System.out.println("Erro na consulta: " + e.getMessage());
			return null;
		}
	}

	public ArrayList<Animal> consultarAnimaisUsuario(int id) {
		try {
			ArrayList<Animal> listaAnimais = new ArrayList<Animal>();
			Connection conn = conexaoDB.abreConexao();
			PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM animal where usuario_id =" + id + ";");
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Animal animal = new Animal();

				animal.setId(resultSet.getInt("id"));
				animal.setNome(resultSet.getString("nome"));
				animal.setDescricao(resultSet.getString("descricao"));
				animal.setSexo(resultSet.getString("sexo"));
				animal.setDataNascimento(resultSet.getString("datanascimento"));
				animal.setCidade(resultSet.getString("cidade"));
				animal.setValorDoacao(resultSet.getDouble("valordoacao"));
				animal.setCaminhoFoto(resultSet.getString("imagem"));
				TipoDAO tipoDAO = new TipoDAO();
				animal.setTipo(tipoDAO.consultaTipoId(resultSet.getInt("tipo_id")));
				UsuarioDAO usuarioDAO = new UsuarioDAO();
				animal.setUsuario(usuarioDAO.consultarUsuarioId(resultSet.getInt("usuario_id")));
				listaAnimais.add(animal);
			}
			conn.close();
			return listaAnimais;
		} catch (Exception e) {
			System.out.println("Erro na consulta: " + e.getMessage());
			return null;
		}
	}

	public ArrayList<Animal> consultarAnimaisPor(String pesquisa) {
		try {
			String query;
			query = "select ani.*, tip.nome nome from animal ani inner join tipo tip on ani.tipo_id = tip.id where ani.nome like '%"
					+ pesquisa + "%' or ani.sexo like '%" + pesquisa + "%' or ani.cidade like '%" + pesquisa
					+ "%' or tip.nome like '" + pesquisa + "%' ORDER BY ani.id  ASC;";
			// select ani.*, tip.nome nome from animal ani inner join tipo tip on
			// ani.tipo_id = tip.id where ani.nome like '%cac%' or ani.sexo like '%cac%' or
			// ani.cidade like '%cac%' or tip.nome like '%cac%';

			ArrayList<Animal> listaAnimais = new ArrayList<Animal>();
			Connection conn = conexaoDB.abreConexao();
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Animal animal = new Animal();

				animal.setId(resultSet.getInt("id"));
				animal.setNome(resultSet.getString("nome"));
				animal.setDescricao(resultSet.getString("descricao"));
				animal.setSexo(resultSet.getString("sexo"));
				animal.setDataNascimento(resultSet.getString("datanascimento"));
				animal.setCidade(resultSet.getString("cidade"));
				animal.setValorDoacao(resultSet.getDouble("valordoacao"));
				animal.setCaminhoFoto(resultSet.getString("imagem"));
				TipoDAO tipoDAO = new TipoDAO();
				animal.setTipo(tipoDAO.consultaTipoId(resultSet.getInt("tipo_id")));
				UsuarioDAO usuarioDAO = new UsuarioDAO();
				animal.setUsuario(usuarioDAO.consultarUsuarioId(resultSet.getInt("usuario_id")));
				listaAnimais.add(animal);
			}
			conn.close();
			return listaAnimais;
		} catch (Exception e) {
			System.out.println("Erro na consulta: " + e.getMessage());
			return null;
		}
	}

	public boolean incluirAnimal(Animal animal) {
		try {
			Connection conn = conexaoDB.abreConexao();
			Statement statement = conn.createStatement();
			String values = "'" + animal.getNome() + "', '" + animal.getDescricao() + "', '" + animal.getSexo() + "', '"
					+ animal.getDataNascimento() + "', '" + animal.getCidade() + "', " + animal.getValorDoacao() + ", '"
					+ animal.getCaminhoFoto() + "', " + animal.getTipo().getId() + "," + animal.getUsuario().getId();
			statement.executeUpdate(
					"INSERT INTO animal(nome, descricao, sexo, datanascimento, cidade, valordoacao, imagem, tipo_id, usuario_id) VALUES("
							+ values + ");");
			conn.close();

			return true;
		} catch (Exception e) {
			System.out.println("Erro na inclusão: " + e.getMessage());
			return false;
		}
	}

	public boolean alterarAnimal(Animal animal) {
		try {
			Connection conn = conexaoDB.abreConexao();
			Statement statement = conn.createStatement();
			String set = " nome='" + animal.getNome() + "', descricao = '" + animal.getDescricao() + "', sexo = '"
					+ animal.getSexo() + "', datanascimento = '" + animal.getDataNascimento() + "', cidade = '"
					+ animal.getCidade() + "', valordoacao = " + animal.getValorDoacao() + ", imagem = '"
					+ animal.getCaminhoFoto() + "', tipo_id =" + animal.getTipo().getId();			
			
			if (statement.executeUpdate("UPDATE animal SET " + set + " WHERE id = " + animal.getId() + ";") != 0) {
				conn.close();
				return true;
			}

			conn.close();
			return false;
		} catch (Exception e) {
			System.out.println("Erro na Alterar: " + e.getMessage());
			return false;
		}
	}

	public boolean excluirAnimal(int id) {
		try {
			Connection conn = conexaoDB.abreConexao();
			Statement statement = conn.createStatement();

			if (statement.executeUpdate("DELETE FROM animal WHERE id = " + id + ";") != 0) {
				conn.close();
				return true;
			}
			conn.close();
			return false;
		} catch (Exception e) {
			System.out.println("Erro na Exclusão: " + e.getMessage());
			return false;
		}
	}

}
