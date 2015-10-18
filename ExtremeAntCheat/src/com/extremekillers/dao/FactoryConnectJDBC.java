package com.extremekillers.dao;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class FactoryConnectJDBC {

	/***
	 * usuairo = extremekillers
	 * email = giovanasilveiracardoso@gmail.com
	 * @throws Exception 
	 */
	
	private static String usuario = null;
	private static String senha = null;
	private static String url = null;
	private static String driver = "org.postgresql.Driver";
	
	private static void createUrlServer() {
		try{
			if(new File("setup-jaderson//dev.properties").exists()){
				usuario = "postgres";
				senha = "admin";
				url = "jdbc:postgresql://localhost:5432/extremeKillers";
			}else{
				usuario = "berti_bd";
				senha = "Jb!1mexupa";
				url = "jdbc:postgresql://216.218.192.170:5432/berti_extreme";
			}
		}catch(Exception e){
			usuario = "berti_bd";
			senha = "Jb!1mexupa";
			url = "jdbc:postgresql://216.218.192.170:5432/berti_extreme";
		}
	}
	
	public static void main(String[] args) {
		if(FactoryConnectJDBC.getConnection() != null){
			System.out.println("ok");
		}else{
			System.out.println("nao deus");
		}
	}
	
	public static Connection getConnection(){
		try {
			createUrlServer();
			Class.forName(driver);
			Connection connection = DriverManager.getConnection(url,usuario,senha);
			return connection;
		 } catch (SQLException e) {
  			 e.printStackTrace();
			 System.err.println("Erro ao conectar");
			 return null;
	     } catch (ClassNotFoundException e) {
	    	 e.printStackTrace();
	    	 System.err.println("Driver de conexão não encontrado");
	    	 return null;
		}
	}

}