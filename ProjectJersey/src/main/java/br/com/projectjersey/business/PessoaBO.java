package br.com.projectjersey.business;

import java.util.List;

import br.com.projectjersey.dao.PessoaDAO;
import br.com.projectjersey.daoImpl.PessoaDAOImpl;
import br.com.projectjersey.entity.Pessoa;

public class PessoaBO{

	private PessoaDAO pessoaDAO;
	
	public PessoaBO() {
		pessoaDAO = new PessoaDAOImpl();
	}	

	public void cadastro(Pessoa pessoa) throws Exception{
		pessoaDAO.cadastro(pessoa);
	}

	public List<Pessoa> findAll() {
		return pessoaDAO.findAll();
	}
	

}
