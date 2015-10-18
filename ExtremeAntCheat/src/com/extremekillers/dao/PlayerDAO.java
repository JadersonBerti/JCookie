package com.extremekillers.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.extremekillers.entity.Player;

public class PlayerDAO {

	private static final String SQL_SELECT_PLAYER_ON = "select * from player_on where serial_player_id = ?";

	public List<Player> findAllPlayerOn(int serialPlayerId){
		try(Connection connection = FactoryConnectJDBC.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_SELECT_PLAYER_ON)){
			
			statement.setInt(1, serialPlayerId);
			
			List<Player> listaPlayerOn = new ArrayList<Player>();
			try(ResultSet rs = statement.executeQuery()){
				while(rs.next()){
					boolean ativo = rs.getBoolean("ativo");
					if(ativo){
						Player player = new Player();
						player.setId(rs.getInt("id"));
						player.setNome(rs.getString("nome_player"));
						player.setAtivo(rs.getBoolean("ativo"));
						player.setData(rs.getDate("data"));
						player.setUsuario_id(rs.getInt("usuario_id"));
						player.setDetalhes(rs.getString("detalhes"));
						player.setSerialLigaId(rs.getInt("serial_player_id"));
						
					    listaPlayerOn.add(player);
					}
				}
			}
			
			return listaPlayerOn;
		}catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}
	}
	
}
