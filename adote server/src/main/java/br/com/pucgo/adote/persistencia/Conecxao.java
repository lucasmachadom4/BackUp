package br.com.pucgo.adote.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conecxao {
	
	 private static final String usuario = "root";
	 private static final String senha = "root";
	 private static final String url = "jdbc:mysql://localhost:3308/adote_db?serverTimezone=UTC";
	 private static final String driver = "com.mysql.cj.jdbc.Driver"; 
	 
	 public Connection abreConexao() {	       
		 Connection conn = null;
		 try {			 
			 try {
					Class.forName(driver);
				} catch (ClassNotFoundException e) {
					System.out.println("Erro Driver: " + e.getMessage());					
				}
			 conn = DriverManager.getConnection(url, usuario, senha);			 
		 }catch(SQLException e){
			 System.out.println("Erro Conexão: " + e.getMessage());
		 }
		 return conn;
	    }
	 

}
