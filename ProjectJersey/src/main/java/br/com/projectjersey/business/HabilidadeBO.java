package br.com.projectjersey.business;

import java.util.List;

import br.com.projectjersey.dao.HabilidadeDAO;
import br.com.projectjersey.daoImpl.HabilidadeDAOImpl;
import br.com.projectjersey.entity.Habilidade;

public class HabilidadeBO {

	private HabilidadeDAO habilidadeDAO;
	
	public HabilidadeBO() {
		habilidadeDAO = new HabilidadeDAOImpl();
	}
	
	public void cadastrar(Habilidade habilidade){
		habilidadeDAO.cadastrar(habilidade);
	}
	
	public List<Habilidade> findAll(){
		return habilidadeDAO.findAll();
	}
}
