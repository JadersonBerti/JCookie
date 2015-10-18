package com.extremekillers.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.extremekillers.entity.ContadorTempo;

public class ContadorTempoDAO {

	private static final String SQL_INSERT_TEMPO = "insert into contador_tempo(serial_player_id,inicio,inicio_admin_id)values(?,?,?)";
	private static final String SQL_UPDATE_TEMPO = "update contador_tempo set termino = ?, termino_admin_id = ?, total_horas = ? where id = ?";
	private static final String SQL_UPDATE_ALL = "update contador_tempo set inico = ?, termino = ?, inicio,inicio_admin_id = ? ,"
			+ " termino_admin_id = ? where id = ?";
	private static final String SQL_DELETA_CONTADOR = "delete from contador_tempo where id = ?";
	private static final String SQL_FIND_BY_ID = "select * from contador_tempo where id = ?";
	private static final String SQL_SELCT_ALL = "select * from contador_tempo";
	private static final String SQL_RETORNA_ULTIMO_ATIVO_BY_SERIAL_PLAYER = "select id,inicio,serial_player_id,inicio_admin_id from contador_tempo where"
			+ " serial_player_id = ? and termino is Null ";
	private static final String SQL_RETORNA_ULTIMO_CONCLUIDA_BY_SERIAL_PLAYER = "select * from contador_tempo "
			+ "where serial_player_id = ? order by id desc limit 1";
	private static final String SQL_SELECT_ALL_BY_SERIAL_PLAYER = "select c.inicio,c.termino,ai.email as admin_inicio, ate.email as admin_termino,c.total_horas from contador_tempo c "
			+ " left join admin ai on (c.inicio_admin_id = ai.id) "
			+ " left join admin ate on (c.termino_admin_id = ate.id) "
			+ " where c.serial_player_id = ? and c.termino is not null";
	private static final String SQL_SELECT_TOTAL_TEMPO = "select total_horas from contador_tempo where serial_player_id = ? "
			+ "and termino is not null";

	private Timestamp converteDateSQL(Date date){
		return new Timestamp(date.getTime());
	}
	
	public Integer insertTempoInContador(int serialPalyerId, Date inicio,int adminId) {
		try(Connection connection = FactoryConnectJDBC.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_TEMPO, 
						PreparedStatement.RETURN_GENERATED_KEYS)){
				
			preparedStatement.setInt(1, serialPalyerId);
			preparedStatement.setTimestamp(2, this.converteDateSQL(inicio));
			preparedStatement.setInt(3, adminId);
			preparedStatement.execute();
			
			try(ResultSet rs = preparedStatement.getGeneratedKeys()){
				if(rs.next()){
					return rs.getInt(1);
				}
			}
			
			return null;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean updateContadorTempo(int id, Date termino, int terminoAdminId, String totalTempo) {
		try(Connection connection = FactoryConnectJDBC.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_TEMPO)){
				
			preparedStatement.setTimestamp(1, this.converteDateSQL(termino));
			preparedStatement.setInt(2, terminoAdminId);
			preparedStatement.setString(3, totalTempo);
			preparedStatement.setInt(4, id);
			preparedStatement.executeUpdate();
			
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean updateAllContadorTempo(int id, Date inicio, Date termino,int inicioAdminId, int terminoAdminId) {
		try(Connection connection = FactoryConnectJDBC.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_ALL)){
			
			
			preparedStatement.setTimestamp(1, this.converteDateSQL(inicio));
			preparedStatement.setTimestamp(2, this.converteDateSQL(termino));
			preparedStatement.setInt(3, inicioAdminId);
			preparedStatement.setInt(4, terminoAdminId);
			preparedStatement.setInt(5, id);
			preparedStatement.executeUpdate();
			
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean deletaContador(int id) {
		try(Connection connection = FactoryConnectJDBC.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETA_CONTADOR)){
			
			
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
			
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public ContadorTempo findById(int id) {
		try(Connection connection = FactoryConnectJDBC.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_BY_ID)){
			
			preparedStatement.setInt(1, id);
			try(ResultSet rs = preparedStatement.executeQuery()){
				if(rs.next()){
					ContadorTempo contadorTempo = new ContadorTempo();
					contadorTempo.setId(rs.getInt("id"));
					contadorTempo.setInicio(new Date(rs.getTimestamp("inicio").getTime()));
					contadorTempo.setTermino(rs.getDate("termino") != null ? 
							new Date(rs.getTimestamp("termino").getTime()) : null);
					contadorTempo.setInicio_admin_id(rs.getInt("inicio_admin_id"));
					contadorTempo.setTermino_admin_id(rs.getInt("termino_admin_id") != 0 ? 
							rs.getInt("termino_admin_id") : null);
					contadorTempo.setSerial_player_id(rs.getInt("serial_player_id"));
					contadorTempo.setTotalHoras(rs.getString("total_horas") != null ? rs.getString("total_horas") : null);
					
					return contadorTempo;
				}
			}
			
			return null;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<ContadorTempo> getContadorTempos() {
		try(Connection connection = FactoryConnectJDBC.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELCT_ALL)){
			
			List<ContadorTempo> contadorTempos = new ArrayList<>();
			try(ResultSet rs = preparedStatement.executeQuery()){
				while(rs.next()){
					ContadorTempo contadorTempo = new ContadorTempo();
					contadorTempo.setId(rs.getInt("id"));
					contadorTempo.setInicio(new Date(rs.getDate("inicio").getTime()));
					contadorTempo.setTermino(rs.getTimestamp("termino") != null ? 
							new Date(rs.getTimestamp("termino").getTime()) : null);
					contadorTempo.setInicio_admin_id(rs.getInt("inicio_admin_id"));
					contadorTempo.setTermino_admin_id(rs.getInt("termino_admin_id") != 0 ? 
							rs.getInt("termino_admin_id") : null);
					contadorTempo.setSerial_player_id(rs.getInt("serial_player_id"));
					contadorTempo.setTotalHoras(rs.getString("total_horas") != null ? rs.getString("total_horas") : null);
					
					contadorTempos.add(contadorTempo);
				}
			}
			
			return contadorTempos;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public ContadorTempo retornaUltimoContadorAtivo(int serial_player_id) {
		try(Connection connection = FactoryConnectJDBC.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQL_RETORNA_ULTIMO_ATIVO_BY_SERIAL_PLAYER)){
			
			preparedStatement.setInt(1, serial_player_id);
			try(ResultSet rs = preparedStatement.executeQuery()){
				if(rs.next()){
					ContadorTempo contadorTempo = new ContadorTempo();
					contadorTempo.setId(rs.getInt("id"));
					contadorTempo.setInicio(new Date(rs.getTimestamp("inicio").getTime()));
					contadorTempo.setInicio_admin_id(rs.getInt("inicio_admin_id"));
					contadorTempo.setSerial_player_id(rs.getInt("serial_player_id"));
					
					return contadorTempo;
				}
			}
			
			return new ContadorTempo();
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public ContadorTempo retornaUtilmaConcluidaBySerialPlayerId(Integer serial_player_id) {
		try(Connection connection = FactoryConnectJDBC.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQL_RETORNA_ULTIMO_CONCLUIDA_BY_SERIAL_PLAYER)){
			
			preparedStatement.setInt(1, serial_player_id);
			try(ResultSet rs = preparedStatement.executeQuery()){
				if(rs.next()){
					ContadorTempo contadorTempo = new ContadorTempo();
					contadorTempo.setId(rs.getInt("id"));
					contadorTempo.setInicio(new Date(rs.getTimestamp("inicio").getTime()));
					contadorTempo.setTermino(rs.getDate("termino") != null ? 
							new Date(rs.getTimestamp("termino").getTime()) : null);
					contadorTempo.setInicio_admin_id(rs.getInt("inicio_admin_id"));
					contadorTempo.setTermino_admin_id(rs.getInt("termino_admin_id") != 0 ? 
							rs.getInt("termino_admin_id") : null);
					contadorTempo.setSerial_player_id(rs.getInt("serial_player_id"));
					contadorTempo.setTotalHoras(rs.getString("total_horas") != null ? rs.getString("total_horas") : null);
					
					return contadorTempo;
				}
			}
			
			return new ContadorTempo();
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<ContadorTempo> getContadorTemposBySerialPlayer(Integer serial_player_id) {
		try(Connection connection = FactoryConnectJDBC.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ALL_BY_SERIAL_PLAYER)){
			
			preparedStatement.setInt(1, serial_player_id);
			
			List<ContadorTempo> contadorTempos = new ArrayList<>();
			try(ResultSet rs = preparedStatement.executeQuery()){
				while(rs.next()){
					ContadorTempo contadorTempo = new ContadorTempo();
					contadorTempo.setInicio(new Timestamp(rs.getTimestamp("inicio").getTime()));
					contadorTempo.setNomeAdminIncio(rs.getString("admin_inicio"));
					contadorTempo.setTermino(rs.getTimestamp("termino"));
					contadorTempo.setNomeAdminTermino(rs.getString("admin_termino"));
					contadorTempo.setTotalHoras(rs.getString("total_horas") != null ? rs.getString("total_horas") : null);
					
					contadorTempos.add(contadorTempo);
				}
			}
			
			return contadorTempos;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}	
	
	public List<String> getTotalTempoGasto(Integer serial_player_id) {
		try(Connection connection = FactoryConnectJDBC.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_TOTAL_TEMPO)){
			
			preparedStatement.setInt(1, serial_player_id);
			
			List<String> tempoGasto = new ArrayList<>();
			try(ResultSet rs = preparedStatement.executeQuery()){
				while(rs.next()){
					tempoGasto.add(rs.getString("total_horas"));
				}
			}
			
			return tempoGasto;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}	
	
}