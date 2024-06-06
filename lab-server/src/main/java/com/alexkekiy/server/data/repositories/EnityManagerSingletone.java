package com.alexkekiy.server.data.repositories;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EnityManagerSingletone {
    private final EntityManager em;
    private static EnityManagerSingletone instance = null;
     static EntityManager getEntityManager(){
        if(instance ==null){
            EntityManagerFactory emf = Persistence.createEntityManagerFactory( "local" );
            EntityManager em1 = emf.createEntityManager();
            instance = new EnityManagerSingletone(em1);
        }
        return instance.em;
    }

    public EnityManagerSingletone(EntityManager em) {
        this.em = em;
    }
}
