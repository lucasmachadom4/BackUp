package br.com.pucgo.adote.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import br.com.pucgo.adote.entidade.Animal;
import br.com.pucgo.adote.entidade.Usuario;

public class UsuarioDAO {

	private Conecxao conexaoDB = new Conecxao();

	public Usuario altenticaUsuario(String email) {
		try {
			Usuario usuario = new Usuario();
			Connection conn = conexaoDB.abreConexao();
			// Statement statement = conn.createStatement();
			PreparedStatement preparedStatement = conn
					.prepareStatement("SELECT email, senha FROM usuario where email = '" + email + "';");
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				usuario.setEmail(resultSet.getString("email"));
				usuario.setSenha(resultSet.getString("senha"));
			}
			conn.close();
			return usuario;
		} catch (Exception e) {
			System.out.println("Erro ao altenticar login: " + e.getMessage());
			return null;
		}
	}

	public Usuario consultarInfUsuario(String email) {
		try {
			Usuario usuario = new Usuario();
			Connection conn = conexaoDB.abreConexao();
			PreparedStatement preparedStatement = conn
					.prepareStatement("SELECT * FROM usuario where email = '" + email + "';");
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				usuario.setId( resultSet.getInt("id") );
				usuario.setNome(resultSet.getString("nome"));
				usuario.setEmail(resultSet.getString("email"));
				usuario.setTelefone1(resultSet.getString("telefone1"));
				usuario.setTelefone2(resultSet.getString("telefone2"));
			}
			conn.close();
			return usuario;
		} catch (Exception e) {
			return null;
		}
	}
	
	public Usuario consultarUsuarioId(int id) {
		try {
			Usuario usuario = new Usuario();
			Connection conn = conexaoDB.abreConexao();
			PreparedStatement preparedStatement = conn
					.prepareStatement("SELECT * FROM usuario where id = " + id + ";");
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				usuario.setId( resultSet.getInt("id") );
				usuario.setNome(resultSet.getString("nome"));
				usuario.setEmail(resultSet.getString("email"));
				usuario.setTelefone1(resultSet.getString("telefone1"));
				usuario.setTelefone2(resultSet.getString("telefone2"));
			}
			conn.close();
			return usuario;
		} catch (Exception e) {
			return null;
		}
	}

	public boolean incluirUsuario(Usuario usuario) {
		try {
			Connection conn = conexaoDB.abreConexao();
			Statement statement = conn.createStatement();
			String values = "'" + usuario.getNome() + "', '" + usuario.getEmail() + "', '" + usuario.getSenha() + "', '"
					+ usuario.getTelefone1() + "', '" + usuario.getTelefone2() + "'";
			statement.executeUpdate(
					"INSERT INTO usuario(nome, email, senha, telefone1, telefone2) VALUES(" + values + ");");
			conn.close();

			return true;
		} catch (Exception e) {
			System.out.println("Erro na inclusão: " + e.getMessage());
			return false;
		}
	}

	public boolean alterarUsuario(Usuario usuario) {
		try {
			Connection conn = conexaoDB.abreConexao();
			Statement statement = conn.createStatement();
			String set = "nome='" + usuario.getNome() + "', email='" + usuario.getEmail() + "', senha='"
					+ usuario.getSenha() + "', telefone1='" + usuario.getTelefone1() + "', telefone2='"
					+ usuario.getTelefone2() + "'";
			
			if(statement.executeUpdate("UPDATE usuario SET " + set + " WHERE id = " + usuario.getId() + ";") != 0) {
				conn.close();
				return true;
			}
			
			conn.close();
			return false;
		} catch (Exception e) {
			System.out.println("Erro na inclusão: " + e.getMessage());
			return false;
		}
	}

	public boolean excluirUsuario(int id) {
		try {
			Connection conn = conexaoDB.abreConexao();
			Statement statement = conn.createStatement();
			AnimalDAO animalDAO = new AnimalDAO();
			ArrayList<Animal> listaAnimals = animalDAO.consultarAnimaisUsuario(id);
			for(int i=0; i<listaAnimals.size(); i++) {				
				if(!animalDAO.excluirAnimal( listaAnimals.get(i).getId() )) {
					System.out.println("Erro na Exclusão dos animais do usuario" );
				}				
			}
			
			if(statement.executeUpdate("DELETE FROM usuario WHERE id = " + id + ";") != 0) {
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
