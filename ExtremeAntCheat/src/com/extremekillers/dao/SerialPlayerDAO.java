package com.extremekillers.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.extremekillers.entity.SerialPlayer;

public class SerialPlayerDAO {

	private static final String SQL_SELECT_FIND_HASH = "select * from serial_player where serial_hash = ?";
	private static final String SQL_SELECT_FIND_ID = "select * from serial_player where id = ?";
	private static final String SQL_UPDATE_STATUS_SERVIDOR = "update serial_player set status_servidor = ? where id = ?";
	private static final String SQL_SELECT_ALL = "select * from serial_player";
	private static final String SQL_DELETE = "delete from serial_player where id = ?";
	private static final String SQL_UPDATE = "update serial_player set serial_hash=?, remetente=?, numero_chaves=?, "
			+ "status_servidor=?, tempo_liberado=? where id = ?";

	public SerialPlayer findBySerialHash(String hash) {
		try(Connection connection = FactoryConnectJDBC.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_SELECT_FIND_HASH)){
			
			statement.setString(1, hash);
			SerialPlayer serialPlayer = new SerialPlayer();
			try(ResultSet rs = statement.executeQuery()){
				if(rs.next()) {
					serialPlayer.setId(rs.getInt("id"));
					serialPlayer.setSerialHash(rs.getString("serial_hash"));
					serialPlayer.setRemetente(rs.getString("remetente"));
					serialPlayer.setNumeroChaves(rs.getInt("numero_chaves"));
				}
			}
			
			return serialPlayer;
		}catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}
	}

	public SerialPlayer findById(int id) {
		try(Connection connection = FactoryConnectJDBC.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_FIND_ID)){
			
			preparedStatement.setInt(1, id);
			try(ResultSet rs = preparedStatement.executeQuery()){
				if(rs.next()){
					SerialPlayer serialPlayer = new SerialPlayer();
					serialPlayer.setId(rs.getInt("id"));
					serialPlayer.setNumeroChaves(rs.getInt("numero_chaves"));
					serialPlayer.setRemetente(rs.getString("remetente"));
					serialPlayer.setSerialHash(rs.getString("serial_hash"));
					serialPlayer.setStatusServidor(rs.getBoolean("status_servidor"));
					serialPlayer.setTempoLiberado(rs.getString("tempo_liberado"));
					
					return serialPlayer;
				}
			}
			
			return null;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean updateStatusServidor(Integer id, boolean flag) {
		try(Connection connection = FactoryConnectJDBC.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_STATUS_SERVIDOR)){
			
			preparedStatement.setBoolean(1, flag);
			preparedStatement.setInt(2, id);
			preparedStatement.executeUpdate();
			
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<SerialPlayer> findAll() {
		try(Connection connection = FactoryConnectJDBC.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ALL);
				ResultSet rs = preparedStatement.executeQuery()){
			
			List<SerialPlayer> serialPlayers = new ArrayList<>();
			while(rs.next()){
				SerialPlayer serialPlayer = new SerialPlayer();
				serialPlayer.setId(rs.getInt("id"));
				serialPlayer.setNumeroChaves(rs.getInt("numero_chaves"));
				serialPlayer.setRemetente(rs.getString("remetente"));
				serialPlayer.setSerialHash(rs.getString("serial_hash"));
				serialPlayer.setStatusServidor(rs.getBoolean("status_servidor"));
				serialPlayer.setTempoLiberado(rs.getString("tempo_liberado"));
				
				serialPlayers.add(serialPlayer);
			}
			
			return serialPlayers;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean delete(int id) {
		try(Connection connection = FactoryConnectJDBC.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE)){
			
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
			
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean update(SerialPlayer serialPlayer) {
		try(Connection connection = FactoryConnectJDBC.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE)){
			
			preparedStatement.setString(1, serialPlayer.getSerialHash());
			preparedStatement.setString(2, serialPlayer.getRemetente());
			preparedStatement.setInt(3, serialPlayer.getNumeroChaves());
			preparedStatement.setBoolean(4, serialPlayer.isStatusServidor());
			preparedStatement.setString(5, serialPlayer.getTempoLiberado());
			preparedStatement.setInt(6, serialPlayer.getId());
			preparedStatement.executeUpdate();
			
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}