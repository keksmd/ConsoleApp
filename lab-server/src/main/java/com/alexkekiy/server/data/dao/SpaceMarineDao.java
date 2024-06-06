package com.alexkekiy.server.data.dao;

import com.alexkekiy.server.data.entities.SpaceMarine;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

public class SpaceMarineDao implements Dao<SpaceMarine> {
    private final EntityManager em;
    private final CriteriaBuilder cb;
    private final CriteriaQuery<SpaceMarine> query;
    private final Root<SpaceMarine> root;

    public SpaceMarineDao(EntityManager em) {
        this.em = em;

        cb = em.getCriteriaBuilder();
        query = cb.createQuery(SpaceMarine.class);
        root = query.from(SpaceMarine.class);

    }

    public List<SpaceMarine> getAllByUserId(long userId) {
        try {
            return em.createQuery(query.where(cb.equal(root.get("user_id"), userId))).getResultList();
        } catch (Exception e) {
            return List.of();
        }
    }

    public List<SpaceMarine> getAll() {
        try {
            query.select(root);
            return em.createQuery(query).getResultList();
        } catch (Exception e) {
            return List.of();
        }
    }

    @Override
    public void save(SpaceMarine spm) {
        em.persist(spm);
    }

    @Override
    public Optional<SpaceMarine> get(long id) {
        Optional<SpaceMarine> spm;
        try {
            spm = Optional.ofNullable(em.find(SpaceMarine.class, id));
        } catch (Exception e) {
            spm = Optional.empty();
        }
        return spm;
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
    public void delete(SpaceMarine spm) {
        em.detach(spm);
    }

    @Override
    public void update(SpaceMarine spm) {
        em.merge(spm);
    }
}