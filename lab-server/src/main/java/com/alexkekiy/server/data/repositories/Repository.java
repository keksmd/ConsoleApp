package com.alexkekiy.server.data.repositories;

import com.alexkekiy.server.data.dao.Dao;
import lombok.Getter;

import java.util.Optional;

/**
 * класс-шаблон для создания классов repository-слоя,инкапсулирующего dao методы и работу с транзакциями
 *
 * @param <T> - entity класс
 */

@Getter
public class Repository<T> {
    private static Repository instance = null;
    private final Dao<T> dao;

    Repository(Dao<T> dao) {
        this.dao = dao;

    }

    public static Repository getRepository(Dao dao) {
        if (instance == null) {
            instance = new Repository(dao);
        }
        return instance;
    }

    public void update(T entity) {
        dao.beginTransaction();
        try {
            dao.update(entity);
        } catch (Exception e) {
            e.printStackTrace();
            dao.rollback();
        }
        dao.commit();
    }

    public void remove(T entity) {
        dao.beginTransaction();
        try {
            dao.delete(entity);
        } catch (Exception e) {
            e.printStackTrace();
            dao.rollback();
        }
        dao.commit();
    }

    public void add(T entity) {
        try {
            dao.beginTransaction();
            try {
                dao.save(entity);
            } catch (Exception e) {
                e.printStackTrace();
                dao.rollback();
            }
            dao.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Optional<T> get(int id) {
        dao.beginTransaction();
        Optional<T> optional;
        try {
            optional = dao.get(id);

        } catch (Exception e) {
            e.printStackTrace();
            dao.rollback();
            return Optional.empty();
        }
        dao.commit();

        return optional;

    }

}
