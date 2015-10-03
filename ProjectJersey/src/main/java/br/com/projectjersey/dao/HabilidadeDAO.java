package br.com.projectjersey.dao;

import java.util.List;

import br.com.projectjersey.entity.Habilidade;

public interface HabilidadeDAO {

	public void cadastrar(Habilidade habilidade);
	
	public void atualizar(Habilidade habilidade);
	
	public Habilidade findById(Integer id);

	public List<Habilidade> findAll();
	
}
