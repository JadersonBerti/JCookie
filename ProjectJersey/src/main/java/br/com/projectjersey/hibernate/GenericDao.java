package br.com.projectjersey.hibernate;

import java.io.Serializable;

import javax.persistence.EntityManager;

public abstract class GenericDao implements Serializable {

    private static final long serialVersionUID = 1L;

    private EntityManager entityManager;

    protected EntityManager getEntityManager() {
        if (entityManager == null || !entityManager.isOpen()) {
            getNovoEntityManager();
        }
        return entityManager;
    }

    protected void commitAndCloseTransaction() {
        entityManager.getTransaction().commit();
        closeEntityManager();
    }

    public void closeEntityManager() {
        entityManager.close();
    }

    protected void beginTransaction() {
        getNovoEntityManager();
        entityManager.getTransaction().begin();
    }

    private void getNovoEntityManager() {
        entityManager = JPAUtil.getEntityManager();
    }

}