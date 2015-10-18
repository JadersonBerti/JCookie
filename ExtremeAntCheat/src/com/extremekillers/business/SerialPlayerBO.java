package com.extremekillers.business;

import java.util.List;

import com.extremekillers.dao.SerialPlayerDAO;
import com.extremekillers.dao.UsuarioDAO;
import com.extremekillers.entity.SerialPlayer;

public class SerialPlayerBO {

	private SerialPlayerDAO serialPlayerDAO;
	
	public SerialPlayerBO() {
		serialPlayerDAO = new SerialPlayerDAO();
	}
	
	public boolean verificaChave(String serialHash) {
		//Retorna um serial atraves da cache hash
		SerialPlayer serialPlayer = serialPlayerDAO.findBySerialHash(serialHash);
		
		//verifica se o serial e valido
		if(serialPlayer.isNotNull()){
			Integer numeroContasUtilizadas = new UsuarioDAO().getCountSerialHashToUsuario(serialPlayer.getId());
			//Verifica se ainda tem conta liberada
			return (numeroContasUtilizadas != null && numeroContasUtilizadas < serialPlayer.getNumeroChaves()); 	
		}
		
		return false;
	}
	
	public boolean isTempoAtingido(String tempoLiberado, int serialPlayerId){
		String tempoUtilizado = new ContadorTempoBO().getContadorTempoRelatorio(serialPlayerId).replaceAll(" ", "");
		tempoLiberado = tempoLiberado.replaceAll(" ", "");
		
		String[] hmsLiberado = tempoLiberado.split(":");
		Integer hLiberado = Integer.valueOf(hmsLiberado[0]);
		Integer mLiberado = Integer.valueOf(hmsLiberado[1]);
		Integer sLiberado = Integer.valueOf(hmsLiberado[2]);
		
		String[] hmsUtilizado = tempoUtilizado.split(":");
		Integer hUtilizado = Integer.valueOf(hmsUtilizado[1]);
		Integer mUtilizado = Integer.valueOf(hmsUtilizado[2]);
		Integer sUtilizado = Integer.valueOf(hmsUtilizado[3]);
		
		if(hUtilizado > hLiberado){
			this.alteraAtomaticoStatusServidor(serialPlayerId);
			return true;
		}else if(hUtilizado == hLiberado && mUtilizado > mLiberado){
			this.alteraAtomaticoStatusServidor(serialPlayerId);
			return true;
		}else if(hUtilizado == hLiberado && mUtilizado == mLiberado && sUtilizado > sLiberado){
			this.alteraAtomaticoStatusServidor(serialPlayerId);
			return true;
		}else{
			return false;
		}
	}

	private void alteraAtomaticoStatusServidor(int serialPlayerId){
		SerialPlayer serialPlayer  = serialPlayerDAO.findById(serialPlayerId);
		if(serialPlayer.isStatusServidor()){
			serialPlayerDAO.updateStatusServidor(serialPlayerId, false);
			serialPlayerDAO.delete(serialPlayerId);
		}
	}
	
	public SerialPlayer findByHash(String hash){
		return serialPlayerDAO.findBySerialHash(hash);
	}
	
	public SerialPlayer findById(int id){
		 SerialPlayer serialPlayer = serialPlayerDAO.findById(id);
		 serialPlayer.setNumeroChavesUsadas(new UsuarioDAO().getCountSerialHashToUsuario(serialPlayer.getId()));
		 
		 return serialPlayer;
	}

	public boolean updateStatusServidor(Integer id, boolean flag) {
		return serialPlayerDAO.updateStatusServidor(id, flag);
	}

	public List<SerialPlayer> findAll() {
		return serialPlayerDAO.findAll();
	}
	
	public Integer getCountSerialToUsuarioId(int id){
		return new UsuarioDAO().getCountSerialHashToUsuario(id);
	}

	public boolean delete(int id) {
		return serialPlayerDAO.delete(id);
	}

	public boolean update(SerialPlayer serialPlayer) {
		return serialPlayerDAO.update(serialPlayer);
	}

}