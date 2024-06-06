package com.alexkekiy.server.data.repositories;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EnityManagerSingletone {
    private static EnityManagerSingletone instance = null;
    private final EntityManager em;

    public EnityManagerSingletone(EntityManager em) {
        this.em = em;
    }

    static EntityManager getEntityManager() {
        if (instance == null) {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("local");
            EntityManager em1 = emf.createEntityManager();
            instance = new EnityManagerSingletone(em1);
        }
        return instance.em;
    }
}
