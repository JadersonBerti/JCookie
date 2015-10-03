package br.com.projectjersey.hibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {
 
    private static EntityManagerFactory factory = null;
    private static EntityManager entityManager = null;


    private static EntityManagerFactory getEntityManagerFactory() {
        if (factory == null) {
            factory = Persistence.createEntityManagerFactory("project_teste");
        }
        return factory;
    }

    public static EntityManager getEntityManager() {
        if (entityManager != null && entityManager.isOpen())
            return entityManager;
        else {
            entityManager = getEntityManagerFactory().createEntityManager();
            return entityManager;
        }
    }

}
