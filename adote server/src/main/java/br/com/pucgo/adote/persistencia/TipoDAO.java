package br.com.pucgo.adote.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import br.com.pucgo.adote.entidade.Tipo;

public class TipoDAO {

	private Conecxao conexaoDB = new Conecxao();

	public ArrayList<Tipo> consultarTipos() {
		try {
			ArrayList<Tipo> listaTipos = new ArrayList<Tipo>();
			Connection conn = conexaoDB.abreConexao();
			Statement statement = conn.createStatement();
			PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM tipo;");
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Tipo tipo = new Tipo();
				tipo.setId( resultSet.getInt("id") );
				tipo.setNome( resultSet.getString("nome") );
				
				listaTipos.add(tipo);
			}			
			conn.close();
			
			return listaTipos;
		} catch (Exception e) {
			System.out.println("Erro na consulta: " + e.getMessage());
			return null;
		}
	}
	
	public Tipo consultaTipoId(int id) {
		try {
			Tipo tipo = new Tipo();
			Connection conn = conexaoDB.abreConexao();
			Statement statement = conn.createStatement();
			PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM tipo where id = " + id +";");
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {				
				tipo.setId( resultSet.getInt("id") );
				tipo.setNome( resultSet.getString("nome") );
			}			
			conn.close();			
			return tipo;
		} catch (Exception e) {
			System.out.println("Erro na consulta: " + e.getMessage());
			return null;
		}
	}
	
	
	public boolean incluirTipo(Tipo tipo) {
		try {
			Connection conn = conexaoDB.abreConexao();
			Statement statement = conn.createStatement();
			statement.executeUpdate("INSERT INTO tipo(nome) VALUES('" + tipo.getNome() + "');");
			conn.close();
			return true;
		} catch (Exception e) {
			System.out.println("Erro na inclusão: " + e.getMessage());
			return false;
		}
	}	
	
	public boolean alterarTipo(Tipo tipo) {
		try {
			Connection conn = conexaoDB.abreConexao();
			Statement statement = conn.createStatement();
			
			if(statement.executeUpdate("UPDATE tipo SET nome='"+ tipo.getNome() +"' WHERE id = " + tipo.getId() + ";") != 0) {
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
	
	public boolean excluirTipo(int id) {
		try {
			Connection conn = conexaoDB.abreConexao();
			Statement statement = conn.createStatement();

			if(statement.executeUpdate("DELETE FROM tipo WHERE id = " + id + ";") != 0) {
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
