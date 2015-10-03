package br.com.projectjersey.daoImpl;

import java.util.List;

import javax.persistence.PersistenceException;

import br.com.projectjersey.dao.PessoaDAO;
import br.com.projectjersey.entity.Habilidade;
import br.com.projectjersey.entity.HabilidadePessoa;
import br.com.projectjersey.entity.Pessoa;
import br.com.projectjersey.hibernate.GenericDao;

public class PessoaDAOImpl extends GenericDao implements PessoaDAO{

	private static final long serialVersionUID = 1L;

	@Override
	public void cadastro(Pessoa pessoa) {
		try {
			beginTransaction();
			getEntityManager().persist(pessoa);
			for(HabilidadePessoa habilidadePessoa : pessoa.getPessoas()){
				Habilidade habilidade = (Habilidade) getEntityManager().createNamedQuery("habilidade.findById")
						.setParameter("id", habilidadePessoa.getHabilidade().getId()).getSingleResult();
				habilidadePessoa.setHabilidade(habilidade);
				getEntityManager().merge(habilidadePessoa);
			}
			commitAndCloseTransaction();
		} catch (PersistenceException e) {
            e.printStackTrace();
        }
	}

	@Override
	public void atualizar(Pessoa pessoa) {
		try {
			beginTransaction();
			getEntityManager().merge(pessoa);
			commitAndCloseTransaction();
		} catch (PersistenceException e) {
            e.printStackTrace();
        }
	}
	
	public void Teste(HabilidadePessoa habilidadePessoa){
		try {
			beginTransaction();
			getEntityManager().merge(habilidadePessoa);
			commitAndCloseTransaction();
		} catch (PersistenceException e) {
            e.printStackTrace();
        }
	}

	@Override
	public List<Pessoa> findAll() {
		try {
			beginTransaction();
			@SuppressWarnings("unchecked")
			List<Pessoa> pessoas = getEntityManager().createNamedQuery("pessoa.findAll").getResultList();
			closeEntityManager();
			
			return pessoas;
		} catch (PersistenceException e) {
            e.printStackTrace();
            return null;
        }
	}
	
}
