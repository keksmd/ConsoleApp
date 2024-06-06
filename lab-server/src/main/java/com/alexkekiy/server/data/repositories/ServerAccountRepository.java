package com.alexkekiy.server.data.repositories;

import com.alexkekiy.server.data.dao.ServerAccountDao;
import com.alexkekiy.server.data.dao.SpaceMarineDao;
import com.alexkekiy.server.data.entities.ServerAccount;

import java.util.Optional;

public class ServerAccountRepository extends Repository<ServerAccount>{
    private static ServerAccountRepository instance = null;
    public static ServerAccountRepository getServerAccountRepository(){
        if(instance ==null){
            instance = new ServerAccountRepository(new ServerAccountDao(EnityManagerSingletone.getEntityManager()),new SpaceMarineDao(EnityManagerSingletone.getEntityManager()));
        }
        return instance;
    }
    private  ServerAccountDao serverAccountDao;
    //private SpaceMarineDao spmDao;
    private ServerAccountRepository(ServerAccountDao serverAccountDao,SpaceMarineDao spmdao) {
        super(serverAccountDao);

        //this.spmDao = spmdao;
    }


    public Optional<ServerAccount> get(String login) {
        serverAccountDao.beginTransaction();
        Optional<ServerAccount> optionalServerAccount;
        try {
            optionalServerAccount = serverAccountDao.get(login);
            //optionalServerAccount.ifPresent(serverAccount -> serverAccount.getDataBaseManager().setCollection(new PriorityQueue<>(serverAccount.getDataBaseManager().getSpaceMarines())));
        }catch (Exception e){ e.printStackTrace();
            serverAccountDao.rollback();
            return Optional.empty();
        }
        serverAccountDao.commit();

        return optionalServerAccount;

    }




}
