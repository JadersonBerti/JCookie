package com.extremekillers.business;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import com.extremekillers.dao.JogadorWarfaceDAO;
import com.extremekillers.entity.JogadorWarface;
import com.extremekillers.entity.Xiter;

public class JogadorWarfaceBO {

	private JogadorWarfaceDAO warfaceDAO; 
	
	public JogadorWarfaceBO() {
		warfaceDAO = new JogadorWarfaceDAO();
	}
	
	public JogadorWarface save(JogadorWarface jogadorWarface) throws Exception {
		jogadorWarface.setId(warfaceDAO.insert(jogadorWarface));
		
		if(jogadorWarface.getId() != null){
			return jogadorWarface;
		}
		
		return null;
	}
	
	@SuppressWarnings("unused")
	private void savefotoFolder(byte[] foto, String projeto,Long id) throws Exception {
		String separator = System.getProperty("file.separator");
		File caminhoFoto = new File(projeto+separator+"uploadfoto"+separator+id+".png");
		if(!caminhoFoto.exists()){
			caminhoFoto.createNewFile();
			try(OutputStream escritor = new FileOutputStream(caminhoFoto)){
				escritor.write(foto);  
			}
		}
	}
	
	public boolean update(JogadorWarface jogadorWarface){
		return warfaceDAO.update(jogadorWarface);
	}

	public List<JogadorWarface> findAll() {
		return warfaceDAO.findAll();		
	}

	public boolean getServidorOn() {
		return warfaceDAO.getServidorOn();
	}

	public List<Xiter> findByBlackList() {
		return warfaceDAO.findByBlackList();
	}

	public boolean isCodigoAntXiter(String codigoAntXiter){
		return warfaceDAO.isCodigoAntXiter(codigoAntXiter);
	}

	public JogadorWarface findByCodigoAntXiter(String codigoAntXiter) {
		return warfaceDAO.findByCodigoAntXiter(codigoAntXiter);
	}

	public JogadorWarface findByCodigoInListJogadorWarface(String codigo,List<JogadorWarface> jogadoresWarfaces) {
		for (JogadorWarface jogadorWarface : jogadoresWarfaces) {
			if(jogadorWarface.getCodigoAntXiter().equals(codigo)){
				return jogadorWarface;
			}
		}
		return null;
	}
	
	public List<JogadorWarface> updateListCurrent(List<JogadorWarface> listaAtualJogador, JogadorWarface jogadorWarfaceAtualizado){
		int index = 0;
		for (JogadorWarface jogadorWarface : listaAtualJogador) {
			if(jogadorWarface.getId().equals(jogadorWarface.getId())){
				 listaAtualJogador.set(index, jogadorWarfaceAtualizado);
				 return listaAtualJogador;
			}
			index++;
		}
		return null;
	}

	public boolean detele(Long id) {
		return warfaceDAO.delete(id);
	}

	public List<JogadorWarface> findBlackListAdminIsView() {
		return warfaceDAO.findBlackListAdminIsView();
	}

	public void updateIsViewAdmin(List<JogadorWarface> blackList) {
		warfaceDAO.updateIsViewAdmin(blackList);
	}

}
