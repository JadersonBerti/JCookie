package com.extremekillers.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.extremekillers.entity.Admin;
import com.extremekillers.entity.Player;
import com.extremekillers.entity.SerialPlayer;

public class AdministrativoDAO {

	private static final String SQL_SELECT_AUTENTICACAO = "select id,serial_player_id,email from admin where email = ? and senha = ?";
	private static final String SQL_INSERT_COMANDO = "insert into comando_antxiter_trom (comando,usuario_id) values (?,?)";
	private static final String SQL_SELECT_RETURN_COMANDO = "select dados from return_comandos where usuario_id = ?"
			+ " order by usuario_id desc limit 1";
	private static final String SQL_DELETE_RETURN_COMANDO = "delete from return_comandos where usuario_id = ?";
	private static final String SQL_AUTENTICA_ADMIN_SYSTEM = "select id from admin_system where login = ? and senha = ?";
	private static final String SQL_INSERT_LIGA = "insert into serial_player(serial_hash,remetente,numero_chaves,status_servidor,tempo_liberado)values(?,?,?,?,?)";
	private static final String SQL_INSERT_ADMIN = "insert into admin(email,senha,serial_player_id)values(?,?,?)";
	private static final String SQL_SELECT_ADMIN_ALL = "select a.id,a.email,a.senha,a.serial_player_id,sp.remetente from admin a "
			+ "left join serial_player sp on (a.serial_player_id = sp.id)";
	private static final String SQL_DELETE_ADMIN = "delete from admin where id = ?";
	private static final String SQL_SELECT_ADMIN = "select * from admin where id = ?";
	private static final String SQL_UPDATE_ADMIN = "update admin set email = ?, senha = ?,serial_player_id = ? where id = ?";

	public Admin autentica(String email, String senha) {
		try(Connection connection = FactoryConnectJDBC.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_SELECT_AUTENTICACAO)){
			
			statement.setString(1, email);
			statement.setString(2, senha);
			try(ResultSet rs = statement.executeQuery()){
				if(rs.next()) {
					if(rs.getInt("id") > 0){
						Admin admin = new Admin();
						admin.setId(rs.getInt("id"));
						admin.setEmail(rs.getString("email"));
						admin.setSerialPlayerId(rs.getInt("serial_player_id"));
						
						return admin;
					}
				}
			}
			
			return null;
		}catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}
	}

	public int executeComandoPrint(int usuario_id) {
		try(Connection connection = FactoryConnectJDBC.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_INSERT_COMANDO, Statement.RETURN_GENERATED_KEYS)){

			int index = 1;
			statement.setInt(index++, 1);
			statement.setInt(index++, usuario_id);
			
			statement.executeUpdate();
			try(ResultSet rs = statement.getGeneratedKeys()){
				if(rs.next()){
					return rs.getInt(1);
				}
			}
			
			return -0;
		}catch (Exception e) {
			System.err.println(e.getMessage());
			return -0;
		}
	}

	public byte[] returnComandoTypePrint(int usuario_id) {
		try(Connection connection = FactoryConnectJDBC.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_SELECT_RETURN_COMANDO)){

			int index = 1;
			statement.setInt(index++, usuario_id);
			
			try(ResultSet rs = statement.executeQuery()){
				if(rs.next()){
					return rs.getBytes("dados");
				}
			}
			
			return null;
		}catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}
	}

	public void executeComandoThemaBaisc(int usuario_id) {
		try(Connection connection = FactoryConnectJDBC.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_INSERT_COMANDO)){

			int index = 1;
			statement.setInt(index++, 3);
			statement.setInt(index++, usuario_id);
			statement.execute();	
			
		}catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	public void deleteReturnComando(int usuario_id) {
		try(Connection connection = FactoryConnectJDBC.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_DELETE_RETURN_COMANDO)){

			int index = 1;
			statement.setInt(index++, usuario_id);
			statement.executeQuery();	
			
		}catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	public boolean autenticaAdminSystem(String email, String senha) {
		try(Connection connection = FactoryConnectJDBC.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_AUTENTICA_ADMIN_SYSTEM)){
				
			statement.setString(1, email);
			statement.setString(2, senha);
			try(ResultSet rs = statement.executeQuery()){
				if(rs.next()){
					if(rs.getInt("id") > 0){
						return true;
					}
				}
 			}
			
			return false;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean insertLiga(SerialPlayer serialPlayer) {
		try(Connection connection = FactoryConnectJDBC.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_INSERT_LIGA)){
			
			int index = 1;
			statement.setString(index++, serialPlayer.getSerialHash());
			statement.setString(index++, serialPlayer.getRemetente());
			statement.setInt(index++, serialPlayer.getNumeroChaves());
			statement.setBoolean(index++, serialPlayer.isStatusServidor());
			statement.setString(index++, serialPlayer.getTempoLiberado());
			statement.execute();
			
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean executeComandoCopieProcesso(int usuario_id) {
		try(Connection connection = FactoryConnectJDBC.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_INSERT_COMANDO)){

			int index = 1;
			statement.setInt(index++, 2);
			statement.setInt(index++, usuario_id);
			statement.execute();	
			
			return true;
		}catch (Exception e) {
			System.err.println(e.getMessage());
			return false;
		}
	}

	public boolean cadastrarAdmin(Admin admin) {
		try(Connection connection = FactoryConnectJDBC.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_INSERT_ADMIN)){
			
			int index = 1;
			statement.setString(index++, admin.getEmail());
			statement.setString(index++, admin.getSenha());
			statement.setInt(index++, admin.getSerialPlayerId());
			statement.execute();
			
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<Admin> findAdminAll() {
		try(Connection connection = FactoryConnectJDBC.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ADMIN_ALL);
				ResultSet rs = preparedStatement.executeQuery()){
			
			List<Admin> admins = new ArrayList<>();
			while(rs.next()){
				Admin admin = new Admin();
				admin.setId(rs.getInt("id"));
				admin.setEmail(rs.getString("email"));
				admin.setSenha(rs.getString("senha"));
				admin.setSerialPlayerId(rs.getInt("serial_player_id"));
				admin.setLigaRemetente(rs.getString("remetente"));
				
				admins.add(admin);
			}
			
			return admins;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean deleteAdmin(int id) {
		try(Connection connection = FactoryConnectJDBC.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_ADMIN)){
			
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
			
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public Admin getAdmin(Integer inicio_admin_id) {
		try(Connection connection = FactoryConnectJDBC.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ADMIN)){
			
			preparedStatement.setInt(1,inicio_admin_id);
			try(ResultSet rs = preparedStatement.executeQuery()){	
				if(rs.next()){
					Admin admin = new Admin();
					admin.setId(rs.getInt("id"));
					admin.setEmail(rs.getString("email"));
					admin.setSenha(rs.getString("senha"));
					admin.setSerialPlayerId(rs.getInt("serial_player_id"));
					admin.setLigaRemetente(rs.getString("remetente"));
					
					return admin;
				}
			}	
			
			return null;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean updateAdmin(Admin admin) {
		try(Connection connection = FactoryConnectJDBC.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_ADMIN)){
				
			preparedStatement.setString(1, admin.getEmail());
			preparedStatement.setString(2, admin.getSenha());
			preparedStatement.setInt(3, admin.getSerialPlayerId());
			preparedStatement.setInt(4, admin.getId());
			preparedStatement.executeUpdate();
			
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public void executaComandoFechaServidor(List<Player> players) {
		try(Connection connection = FactoryConnectJDBC.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_COMANDO)){
			
			for(Player player : players){
				preparedStatement.setInt(1, 4);
				preparedStatement.setInt(2, player.getUsuario_id());
				preparedStatement.execute();
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}


}