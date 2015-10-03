package br.com.projectjersey.dao;

import java.util.List;

import br.com.projectjersey.entity.Pessoa;

public interface PessoaDAO {
	
	public void cadastro(Pessoa pessoa);
	
	public void atualizar(Pessoa pessoa);

	public List<Pessoa> findAll();
	
}
