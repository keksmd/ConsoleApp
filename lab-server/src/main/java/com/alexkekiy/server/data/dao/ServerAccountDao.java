package com.alexkekiy.server.data.dao;

import com.alexkekiy.server.data.entities.ServerAccount;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Optional;

public class ServerAccountDao implements Dao<ServerAccount> {
    private final EntityManager em;
    private final CriteriaBuilder cb;
    private final CriteriaQuery<ServerAccount> query;
    private final Root<ServerAccount> root;

    public ServerAccountDao(EntityManager em) {
        this.em = em;
        cb = em.getCriteriaBuilder();
        query = cb.createQuery(ServerAccount.class);
        root = query.from(ServerAccount.class);
    }

    public Optional<ServerAccount> get(String login) {
        ServerAccount serverAccount = em.createQuery(query.where(cb.equal(root.get("login"), login))).getSingleResult();
        return Optional.ofNullable(serverAccount);
    }

    @Override
    public void commit() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
    }

    @Override
    public void rollback() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
    }

    @Override
    public void beginTransaction() {
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
    }

    @Override
    public void save(ServerAccount serverAccount) {
        em.persist(serverAccount);
    }

    @Override
    public Optional<ServerAccount> get(long id) {
        Optional<ServerAccount> acc;
        try {
            acc = Optional.ofNullable(em.find(ServerAccount.class, id));
        } catch (Exception e) {
            acc = Optional.empty();
        }
        return acc;
    }

    @Override
    public void delete(ServerAccount serverAccount) {
        em.detach(serverAccount);
    }

    @Override
    public void update(ServerAccount serverAccount) {
        em.merge(serverAccount);
    }


}