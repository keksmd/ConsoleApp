package com.alexkekiy.server.data.repositories;

import com.alexkekiy.server.data.dao.ServerAccountDao;
import com.alexkekiy.server.data.entities.ServerAccount;

import java.util.Optional;

public class ServerAccountRepository extends Repository<ServerAccount> {
    private static ServerAccountRepository instance = null;
    private final ServerAccountDao serverAccountDao;

    private ServerAccountRepository(ServerAccountDao serverAccountDao) {
        super(serverAccountDao);
        this.serverAccountDao = serverAccountDao;
    }

    public static ServerAccountRepository getServerAccountRepository() {
        if (instance == null) {
            instance = new ServerAccountRepository(new ServerAccountDao(EnityManagerSingletone.getEntityManager()));
        }
        return instance;
    }

    public Optional<ServerAccount> get(String login) {
        serverAccountDao.beginTransaction();
        Optional<ServerAccount> optionalServerAccount;
        try {
            optionalServerAccount = serverAccountDao.get(login);
        } catch (Exception e) {
            e.printStackTrace();
            serverAccountDao.rollback();
            return Optional.empty();
        }
        serverAccountDao.commit();
        return optionalServerAccount;
    }
}
