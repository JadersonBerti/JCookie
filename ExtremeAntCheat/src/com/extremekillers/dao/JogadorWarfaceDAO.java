package com.extremekillers.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.extremekillers.entity.JogadorWarface;
import com.extremekillers.entity.Xiter;

public class JogadorWarfaceDAO {

	private static final String SQL_INSERT_JOGADOR = "insert into jogador_warface(nome, email, login_warface, nick, sexo, data, codigoAntXiter, foto)"
			+ " values (?,?,?,?,?,?,?,?)";
	private static final String SQL_UPDATE_JOGADOR = "update jogador_warface set nome=?, email=?, login_warface=?, nick=?, sexo=?, data=?, codigoAntXiter=?, foto=? where id = ?";
	private static final String SQL_SELECT_ALL = "select * from jogador_warface";
	private static final String SQL_SELECT_SERVIDOR = "select ativo from servidor";
	private static final String SQL_SELECT_XITER_ALL = "select * from xiter";
	private static final String SQL_SELECT_JOGADOR_WARFACE_CODIGOANTXITER = "select codigoAntXiter from jogador_warface where codigoAntXiter = ?";
	private static final String SQL_SELECT_JOGADOR_WARFACE_FIND = "select id, nome, email, login_warface, nick, sexo, data, codigoAntXiter from jogador_warface where codigoAntXiter = ?";
	private static final String SQL_DELETE_JOGADOR_WARFACE = "delete from jogador_warface where id = ?";
	private static final String SQL_SELECT_IS_VIEW_ADMIN = "select id,nome_xiter,nome_jogador from xiter where is_view_admin = false";
	private static final String SQL_UPDATE_IS_VIEW_ADMIN = "update xiter set is_view_admin = ? where id = ?"; 
	
	public Long insert(JogadorWarface jogadorWarface) {
		try(Connection connection = FactoryConnectJDBC.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_INSERT_JOGADOR, Statement.RETURN_GENERATED_KEYS)){
			
			int index = 1;
			statement.setString(index++, jogadorWarface.getNome());
			statement.setString(index++, jogadorWarface.getEmail());
			statement.setString(index++, jogadorWarface.getLoginWarface());
			statement.setString(index++, jogadorWarface.getNickJogo());
			statement.setString(index++, String.valueOf(jogadorWarface.getSexo()));
			statement.setDate(index++, new Date(jogadorWarface.getDataNacimento().getTime()));
			statement.setString(index++, jogadorWarface.getCodigoAntXiter());
			statement.setBytes(index++, jogadorWarface.getFotoByte());
			
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

	public boolean update(JogadorWarface jogadorWarface) {
		try(Connection connection = FactoryConnectJDBC.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_JOGADOR)){
			
			int index = 1;
			statement.setString(index++, jogadorWarface.getNome());
			statement.setString(index++, jogadorWarface.getEmail());
			statement.setString(index++, jogadorWarface.getLoginWarface());
			statement.setString(index++, jogadorWarface.getNickJogo());
			statement.setString(index++, String.valueOf(jogadorWarface.getSexo()));
			statement.setDate(index++, new Date(jogadorWarface.getDataNacimento().getTime()));
			statement.setString(index++, jogadorWarface.getCodigoAntXiter());
			statement.setBytes(index++, jogadorWarface.getFotoByte());
			
			statement.setInt(index, jogadorWarface.getId().intValue());
			
			statement.executeUpdate();
			return true;
		}catch (Exception e) {
			System.err.println(e.getMessage());
			return false;
		}
		
	}

	public List<JogadorWarface> findAll() {
		try(Connection connection = FactoryConnectJDBC.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL);
				ResultSet rs = statement.executeQuery()){
			
			List<JogadorWarface> jogadorWarfaces = new ArrayList<>();
			while (rs.next()) {
				JogadorWarface warface = new JogadorWarface();
				warface.setId(rs.getLong("id"));
				warface.setNome(rs.getString("nome"));
				warface.setLoginWarface(rs.getString("login_warface") != null && !rs.getString("login_warface").isEmpty() ? rs.getString("login_warface"):"");
				warface.setNickJogo(rs.getString("nick"));
				warface.setEmail(rs.getString("email"));
				warface.setSexo(rs.getString("sexo").charAt(0));
				warface.setDataNacimento(rs.getDate("data"));
				warface.setCodigoAntXiter(rs.getString("codigoAntXiter"));
				warface.setFotoByte(rs.getBytes("foto"));
				
				jogadorWarfaces.add(warface);
			}
			
			return jogadorWarfaces;
		}catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}
	}

	public boolean getServidorOn()  {
		try(Connection connection = FactoryConnectJDBC.getConnection();
			PreparedStatement statement = connection.prepareStatement(SQL_SELECT_SERVIDOR);
			ResultSet rs = statement.executeQuery()){
			
			if(rs.next()){
				return rs.getBoolean("ativo");
			}
			
			return false;
		}catch (Exception e) {
			System.err.println(e.getMessage());
			return false;
		}
	}

	public List<Xiter> findByBlackList() {
		try(Connection connection = FactoryConnectJDBC.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_SELECT_XITER_ALL);
				ResultSet rs = statement.executeQuery()){
			
			List<Xiter> xiters = new ArrayList<Xiter>();
			while (rs.next()) {
				Xiter xiter = new Xiter();
				xiter.setId(rs.getLong("id"));
				xiter.setNomeJogador(rs.getString("nome_jogador") != null ? rs.getString("nome_jogador"):"");
				xiter.setNomeXiter(rs.getString("nome_xiter") != null ? rs.getString("nome_xiter") : "");
				xiter.setDataUsoXiter(rs.getDate("data_usa_xiter"));
				xiter.setSolucionado("");
				
				if(xiter.getNomeJogador().equals("Evertom")){
					xiter.setSolucionado("Pc-Formatado");
				}
				
				if(xiter.getNomeJogador().equals("Binladem")){
					xiter.setSolucionado("Foi apenas o RzSynapse do som");
				}
				
				xiters.add(xiter);
			}
			
			return xiters;
		}catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}
	}
	
	public boolean isCodigoAntXiter(String codigoAntXiter){
		try(Connection connection = FactoryConnectJDBC.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_SELECT_JOGADOR_WARFACE_CODIGOANTXITER)){
			
			statement.setString(1, codigoAntXiter);

			String codigo = null;
			try(ResultSet rs = statement.executeQuery()){
				if (rs.next()) {
					codigo = rs.getString("codigoAntXiter");
				}
			}
			return codigo != null ? true:false;
		}catch (Exception e) {
			System.err.println(e.getMessage());
			return false;
		}
	}

	public JogadorWarface findByCodigoAntXiter(String codigoAntXiter) {
		try(Connection connection = FactoryConnectJDBC.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_SELECT_JOGADOR_WARFACE_FIND)){
			
			statement.setString(1, codigoAntXiter);
			JogadorWarface warface = new JogadorWarface();
			try(ResultSet rs = statement.executeQuery()){
				if(rs.next()) {
					warface.setId(rs.getLong("id"));
					warface.setNome(rs.getString("nome"));
					warface.setLoginWarface(rs.getString("login_warface") != null && !rs.getString("login_warface").isEmpty() ? rs.getString("login_warface"):"");
					warface.setNickJogo(rs.getString("nick"));
					warface.setEmail(rs.getString("email"));
					warface.setSexo(rs.getString("sexo").charAt(0));
					warface.setDataNacimento(rs.getDate("data"));
					warface.setCodigoAntXiter(rs.getString("codigoAntXiter"));
				}
			}
			
			return warface;
		}catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}
	}

	public boolean delete(Long id) {
		try(Connection connection = FactoryConnectJDBC.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_DELETE_JOGADOR_WARFACE)){
			
			statement.setInt(1, id.intValue());
			statement.executeUpdate();
			
			return true;
		}catch (Exception e) {
			System.err.println(e.getMessage());
			return false;
		}
	}

	public List<JogadorWarface> findBlackListAdminIsView() {
		try(Connection connection = FactoryConnectJDBC.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_SELECT_IS_VIEW_ADMIN);
				ResultSet rs = statement.executeQuery()){
				
				List<JogadorWarface> jogadorWarfaces = new ArrayList<JogadorWarface>();
				while (rs.next()) {
					JogadorWarface warface = new JogadorWarface();
					warface.setId(rs.getLong("id"));
					warface.setNickJogo(rs.getString("nome_jogador"));
					warface.setNome(rs.getString("nome_xiter"));
					
					jogadorWarfaces.add(warface);
				}
			
				return jogadorWarfaces;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void updateIsViewAdmin(List<JogadorWarface> blackList) {
		try(Connection connection = FactoryConnectJDBC.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_IS_VIEW_ADMIN)){
			
			for(JogadorWarface jogador : blackList){
				statement.setBoolean(1, true);
				statement.setInt(2, jogador.getId().intValue());
				statement.executeUpdate();
			}
			
		}catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}
