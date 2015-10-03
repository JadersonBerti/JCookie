package br.com.projectjersey.daoImpl;

import java.util.List;

import javax.persistence.PersistenceException;

import br.com.projectjersey.dao.HabilidadeDAO;
import br.com.projectjersey.entity.Habilidade;
import br.com.projectjersey.hibernate.GenericDao;

public class HabilidadeDAOImpl extends GenericDao implements HabilidadeDAO {

	private static final long serialVersionUID = 1L;

	@Override
	public Habilidade findById(Integer id) {
		try{
			beginTransaction();
			Habilidade habilidade = (Habilidade) getEntityManager().createNamedQuery("habilidade.findById")
				.setParameter("id", id).getSingleResult();
			getEntityManager().flush();
			closeEntityManager();
			return habilidade;
		}catch(PersistenceException e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void cadastrar(Habilidade habilidade) {
		try{
			beginTransaction();
			getEntityManager().persist(habilidade);
			commitAndCloseTransaction();
		}catch(PersistenceException e){
			e.printStackTrace();
		}
	}

	@Override
	public void atualizar(Habilidade habilidade) {
		try{
			beginTransaction();
			getEntityManager().merge(habilidade);
			commitAndCloseTransaction();
		}catch(PersistenceException e){
			e.printStackTrace();
		}
	}

	@Override
	public List<Habilidade> findAll() {
		try{
			beginTransaction();
			@SuppressWarnings("unchecked")
			List<Habilidade> habilidades = getEntityManager().createNamedQuery("habilidade.findAll").getResultList();
			closeEntityManager();
			
			return habilidades;
		}catch(PersistenceException e){
			e.printStackTrace();
			return null;
		}
	}

}
