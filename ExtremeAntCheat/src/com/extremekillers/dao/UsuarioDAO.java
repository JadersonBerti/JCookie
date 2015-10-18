package com.extremekillers.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.extremekillers.entity.Usuario;

public class UsuarioDAO {

	private static final String SQL_SELECT_COUNT_SERIAL_PLAYER = "select count(id) from usuario where serial_player_id = ?";
	private static final String SQL_INSERT_USUARIO = "INSERT INTO usuario(nome, email, nick, sexo, senha_ant_xiter, foto, data, serial_player_id) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
	private static final String SQL_SELECT_ALL = "select * from usuario";
	private static final String SQL_SELECT_AUTENTICA = "select * from usuario where senha_ant_xiter = ? and email = ?";
	private static final String SQL_UPDATE_USUARIO = "update usuario set nome=?, email=?, nick=?, sexo=?, senha_ant_xiter=?, foto=?, data=?,"
			+ " serial_player_id=? where id = ? ";
	private static final String SQL_DELETE_USUARIO = "delete from usuario where id = ?";
	private static final String SQL_SELECT_FILTER = "select * from usuario where nick = ? or nome = ?";
	private static final String SQL_COUNT = "select count(id) from usuario";
	private static final String SQL_SELECT_ALL_LIMIT = "select * from usuario order by id desc limit ?";
	private static final String SQL_SELECT_FIND_FILTER = "select * from usuario where id < ? order by id desc limit ?";
	private static final String SQL_VALIDA_NICK = "select id from usuario where nick = ?";

	public Integer getCountSerialHashToUsuario(Integer id) {
		try(Connection connection = FactoryConnectJDBC.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_SELECT_COUNT_SERIAL_PLAYER)){
			
			statement.setInt(1, id);
			try(ResultSet rs = statement.executeQuery()){
				if(rs.next()) {
					return rs.getInt(1);
				}
			}
			
			return -0;
		}catch (Exception e) {
			System.err.println(e.getMessage());
			return -0;
		}
	}

	public Long insert(Usuario usuario) {
		try(Connection connection = FactoryConnectJDBC.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_INSERT_USUARIO, Statement.RETURN_GENERATED_KEYS)){

			
			int index = 1;
			statement.setString(index++, usuario.getNome());
			statement.setString(index++, usuario.getEmail());
			statement.setString(index++, usuario.getNickJogo());
			statement.setString(index++, String.valueOf(usuario.getSexo()));
			statement.setString(index++, usuario.getSenhaAntXiter());
			statement.setBytes(index++, usuario.getFotoByte());
			statement.setDate(index++, new Date(usuario.getDataNacimento().getTime()));
			statement.setInt(index, usuario.getSerialPlayerId());
			
			statement.executeUpdate();
			try(ResultSet rs = statement.getGeneratedKeys()){
				if(rs.next()){
					return rs.getLong(1);
				}
			}
			return null;
		}catch (Exception e) {
			System.err.println("---------------------------------");
			System.err.println(e.getMessage());
			System.err.println("---------------------------------");
			return null;
		}
	}

	public List<Usuario> findAll() {
		try(Connection connection = FactoryConnectJDBC.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL)){
			
			List<Usuario> usuarios = new ArrayList<Usuario>();
			try(ResultSet rs = statement.executeQuery()){
				while(rs.next()){
					Usuario usuario = new Usuario();
					usuario.setNome(rs.getString("nome"));
					usuario.setEmail(rs.getString("email"));
					usuario.setNickJogo(rs.getString("nick"));
					usuario.setSexo(rs.getString("sexo").charAt(0));
					usuario.setSenhaAntXiter(rs.getString("senha_ant_xiter"));
					usuario.setFotoByte(rs.getBytes("foto"));
					usuario.setDataNacimento(rs.getDate("data"));
					usuario.setSerialPlayerId(rs.getInt("serial_player_id"));
					
					usuarios.add(usuario);
				}
			}
		
			return usuarios;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	public Usuario autentica(String senha, String email) {
		try(Connection connection = FactoryConnectJDBC.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_SELECT_AUTENTICA)){
			
			statement.setString(1, senha);
			statement.setString(2, email);
			
			Usuario usuario = new Usuario();
			try(ResultSet rs = statement.executeQuery()){
				if(rs.next()){
					usuario.setId(rs.getLong("id"));
					usuario.setNome(rs.getString("nome"));
					usuario.setEmail(rs.getString("email"));
					usuario.setNickJogo(rs.getString("nick"));
					usuario.setSexo(rs.getString("sexo").charAt(0));
					usuario.setSenhaAntXiter(rs.getString("senha_ant_xiter"));
					usuario.setFotoByte(rs.getBytes("foto"));
					usuario.setDataNacimento(rs.getDate("data"));
					usuario.setSerialPlayerId(rs.getInt("serial_player_id"));
				}
			}

			return usuario;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean update(Usuario usuario) {
		try(Connection connection = FactoryConnectJDBC.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_USUARIO)){

			int index = 1;
			statement.setString(index++, usuario.getNome());
			statement.setString(index++, usuario.getEmail());
			statement.setString(index++, usuario.getNickJogo());
			statement.setString(index++, String.valueOf(usuario.getSexo()));
			statement.setString(index++, usuario.getSenhaAntXiter());
			statement.setBytes(index++, usuario.getFotoByte());
			statement.setDate(index++, new Date(usuario.getDataNacimento().getTime()));
			statement.setInt(index++, usuario.getSerialPlayerId());
			
			statement.setInt(index++, usuario.getId().intValue());
			
			statement.executeUpdate();
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}	
	}

	public boolean delete(Long id) {
		try(Connection connection = FactoryConnectJDBC.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_DELETE_USUARIO)){
			
			statement.setInt(1, id.intValue());
			statement.executeUpdate();
			
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public Usuario findByFilterNameOrNick(String nomeUsuario) {
		try(Connection connection = FactoryConnectJDBC.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_SELECT_FILTER)){
			
			statement.setString(1, nomeUsuario);
			statement.setString(2, nomeUsuario);
			
			Usuario usuario = new Usuario();
			try(ResultSet rs = statement.executeQuery()){
				if(rs.next()){
					usuario.setId(rs.getLong("id"));
					usuario.setNome(rs.getString("nome"));
					usuario.setEmail(rs.getString("email"));
					usuario.setNickJogo(rs.getString("nick"));
					usuario.setSexo(rs.getString("sexo").charAt(0));
					usuario.setSenhaAntXiter(rs.getString("senha_ant_xiter"));
					usuario.setFotoByte(rs.getBytes("foto"));
					usuario.setDataNacimento(rs.getDate("data"));
					usuario.setSerialPlayerId(rs.getInt("serial_player_id"));
				}
			}

			return usuario;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Integer getCountUsuarios() {
		try(Connection connection = FactoryConnectJDBC.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQL_COUNT);
				ResultSet rs = preparedStatement.executeQuery()){
			
			if(rs.next()){
				return rs.getInt(1);
			}
			
			return 0;
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public List<Usuario> findAllLimit(Integer limit) {
		try(Connection connection = FactoryConnectJDBC.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_LIMIT)){

			statement.setInt(1, limit);
			
			List<Usuario> usuarios = new ArrayList<>();
			try(ResultSet rs = statement.executeQuery()){
				while(rs.next()){
					Usuario usuario = new Usuario();
					usuario.setId(rs.getLong("id"));
					usuario.setNome(rs.getString("nome"));
					usuario.setEmail(rs.getString("email"));
					usuario.setNickJogo(rs.getString("nick"));
					usuario.setSexo(rs.getString("sexo").charAt(0));
					usuario.setSenhaAntXiter(rs.getString("senha_ant_xiter"));
					usuario.setFotoByte(rs.getBytes("foto"));
					usuario.setDataNacimento(rs.getDate("data"));
					usuario.setSerialPlayerId(rs.getInt("serial_player_id"));
					
					usuarios.add(usuario);
				}
			}

			return usuarios;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Usuario> findFilter(int id, int limit) {
		try(Connection connection = FactoryConnectJDBC.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_SELECT_FIND_FILTER)){
			
			statement.setInt(1, id);
			statement.setInt(2, limit);
			
			List<Usuario> usuarios = new ArrayList<>();
			try(ResultSet rs = statement.executeQuery()){
				while(rs.next()){
					Usuario usuario = new Usuario();
					usuario.setId(rs.getLong("id"));
					usuario.setNome(rs.getString("nome"));
					usuario.setEmail(rs.getString("email"));
					usuario.setNickJogo(rs.getString("nick"));
					usuario.setSexo(rs.getString("sexo").charAt(0));
					usuario.setSenhaAntXiter(rs.getString("senha_ant_xiter"));
					usuario.setFotoByte(rs.getBytes("foto"));
					usuario.setDataNacimento(rs.getDate("data"));
					usuario.setSerialPlayerId(rs.getInt("serial_player_id"));
					
					usuarios.add(usuario);
				}
			}

			return usuarios;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Integer validaNick(String nickJogo) {
		try(Connection connection = FactoryConnectJDBC.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQL_VALIDA_NICK)){
			
			preparedStatement.setString(1, nickJogo);
			try(ResultSet rs = preparedStatement.executeQuery()){
				if(rs.next()){
					return rs.getInt("id");
				}
			}
			
			return 0;
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

}