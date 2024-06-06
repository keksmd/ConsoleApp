package com.alexkekiy.server.data.repositories;

import com.alexkekiy.server.data.dao.SpaceMarineDao;
import com.alexkekiy.server.data.entities.SpaceMarine;

import java.util.Collection;
import java.util.List;

public class SpaceMarineRepository extends Repository<SpaceMarine>{
    private static SpaceMarineRepository instance = null;
    private final SpaceMarineDao spmDao;
    SpaceMarineRepository(SpaceMarineDao spmDao) {
        super(spmDao);
        this.spmDao = spmDao;
    }
    public static SpaceMarineRepository getSpaceMarineRepository(){
        if(instance ==null){
            instance = new SpaceMarineRepository(new SpaceMarineDao(EnityManagerSingletone.getEntityManager()));
        }
        return instance;
    }
    public List<SpaceMarine> getAll(){
        this.getDao().beginTransaction();
        try {
            return spmDao.getAll();
        }catch (Exception e){ e.printStackTrace();
            spmDao.rollback();
        }
        spmDao.commit();
        return List.of();
    }
    public List<SpaceMarine> getAllByUserId(long userId){
        spmDao.beginTransaction();
        try {
            return spmDao.getAllByUserId(userId)/*.stream().peek(w->spmDao.update(w)).toList()*/;
        }catch (Exception e){ e.printStackTrace();
            spmDao.rollback();
        }
        spmDao.commit();
        return List.of();

    }
    public void removeAll(Collection<SpaceMarine> collection){
        spmDao.beginTransaction();
        try {
            collection.forEach(w -> spmDao.delete(w));
        }catch (Exception e){ e.printStackTrace();
            spmDao.rollback();
        }
        spmDao.commit();
    }
    public void addAll(Collection<SpaceMarine> collection){

        spmDao.beginTransaction();
        try {
            collection.forEach(w->spmDao.save(w));
        }catch (Exception e){ e.printStackTrace();
            spmDao.rollback();
        }
        spmDao.commit();
    }

 

    
    

    
   

   
    
}
