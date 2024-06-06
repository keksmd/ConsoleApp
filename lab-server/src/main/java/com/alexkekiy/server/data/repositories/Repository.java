package com.alexkekiy.server.data.repositories;

import com.alexkekiy.server.data.dao.Dao;
import lombok.Getter;

import java.util.Optional;
/**
 * класс-шаблон для создания классов repository-слоя,инкапсулирующего dao методы и работу с транзакциями
 * @param <T>  - entity класс
 */

public class Repository<T> {
    private static Repository instance = null;
    public static Repository getRepository(Dao dao){
        if(instance ==null){
            instance = new Repository(dao);
        }
        return instance;
    }
    @Getter
    private Dao<T> dao;
    Repository(Dao<T> dao) {
        this.dao = dao;
        
    }

    public void update(T entity) {
        dao.beginTransaction();
        try {
            dao.update(entity);
        }catch (Exception e){ e.printStackTrace();
            dao.rollback();
        }
        dao.commit();
    }

    public void remove(T entity) {
        dao.beginTransaction();
        try {
            dao.delete(entity);
        }catch (Exception e){ e.printStackTrace();
            dao.rollback();
        }
        dao.commit();
    }

    public void add(T entity) {
        System.out.println("начало метода");
        try {
            dao.beginTransaction();
            System.out.println("начали транзакцию");
            try {
                dao.save(entity);
                System.out.println("сохранили");
            } catch (Exception e) {
                e.printStackTrace();
                dao.rollback();
                System.out.println("роллбек");
            }
            dao.commit();
            System.out.println("коммит");
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("конец метода");
    }

    public Optional<T> get(int id) {
        dao.beginTransaction();
        Optional<T> optional;
        try {
            optional = dao.get(id);
            
        }catch (Exception e){ e.printStackTrace();
           dao.rollback();
            return Optional.empty();
        }
        dao.commit();

        return optional;

    }

}
