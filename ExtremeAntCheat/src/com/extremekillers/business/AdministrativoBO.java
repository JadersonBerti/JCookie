package com.extremekillers.business;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.extremekillers.dao.AdministrativoDAO;
import com.extremekillers.entity.Admin;
import com.extremekillers.entity.Player;
import com.extremekillers.entity.SerialPlayer;

public class AdministrativoBO {

	private AdministrativoDAO administrativoDAO;
	
	public AdministrativoBO() {
		administrativoDAO = new AdministrativoDAO();
	}

	public void exportTxt(HttpServletResponse responce, String processos){
		responce.setContentType("txt/plain");
		responce.setHeader("Content-disposition", "attachment; filename=processos.txt");
		
		try(OutputStream outputStream = responce.getOutputStream()){
			outputStream.write(processos.getBytes());
		}catch (IOException io) {
			io.printStackTrace();
		}
	}
	
	public Admin autenticar(String email, String senha) {
		return administrativoDAO.autentica(email, senha);
	}

	public int executeComandoPrint(int usuario_id) {
		 return administrativoDAO.executeComandoPrint(usuario_id);
	}

	public byte[] returnComandoTypePrint(int comandoId) {
		return administrativoDAO.returnComandoTypePrint(comandoId);
	}

	public void executeComandoThemaBaisc(int usuario_id) {
		administrativoDAO.executeComandoThemaBaisc(usuario_id);
	}

	public void deleteRetunrComando(int usuario_id) {
		administrativoDAO.deleteReturnComando(usuario_id);
	}

	public boolean autenticarAdminSystem(String email, String senha) {
		return administrativoDAO.autenticaAdminSystem(email, senha);
	}

	public boolean cadastraLiga(SerialPlayer serialPlayer) {
		serialPlayer.setStatusServidor(false);
		return administrativoDAO.insertLiga(serialPlayer);
	}

	public boolean executeComandoCopieProcesso(int usuario_id) {
		return administrativoDAO.executeComandoCopieProcesso(usuario_id);
	}

	public boolean cadastrarAdmin(Admin admin) {
		return administrativoDAO.cadastrarAdmin(admin);
	}

	public List<Admin> findAdminAll() {
		return administrativoDAO.findAdminAll();
	}

	public boolean deleteAdmin(int id) {
		return administrativoDAO.deleteAdmin(id);
	}

	public Admin getAdmin(Integer inicio_admin_id) {
		return administrativoDAO.getAdmin(inicio_admin_id); 
	}

	public boolean updateAdmin(Admin admin) {
		return administrativoDAO.updateAdmin(admin);
	}

	public void executaComandoFechaServidor(int serialPlayerId)  {
		List<Player> players = new PlayerBO().findAll(serialPlayerId);
		if(players != null && !players.isEmpty()){
			administrativoDAO.executaComandoFechaServidor(players);	
		}
	}

}
