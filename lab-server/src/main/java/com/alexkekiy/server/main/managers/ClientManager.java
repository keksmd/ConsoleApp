package com.alexkekiy.server.main.managers;

import com.alexkekiy.common.exceptions.NoAccountFounded;
import com.alexkekiy.common.utilites.Request;
import com.alexkekiy.common.utilites.Response;
import com.alexkekiy.server.data.entities.ServerAccount;
import com.alexkekiy.server.data.repositories.ServerAccountRepository;
import com.alexkekiy.server.exceptions.InvalidPassword;
import lombok.Getter;
import lombok.Setter;

import java.nio.channels.SocketChannel;
import java.util.Optional;

/**
 * менеджер клиента,упраявляющий его подключением,синхронизацией аккаунта с бд и авторизацией
 */
@Setter
@Getter
public class ClientManager {
    @Getter
    boolean firstMessageFromClient = true;
    SocketChannel channel;
    private ServerAccountRepository accountRepository;
    private ServerAccount user;

    public ClientManager(SocketChannel c) {
        channel = c;
        accountRepository = ServerAccountRepository.getServerAccountRepository();
    }

    public void checkAnswer(Response response, Request request) throws InvalidPassword, NoAccountFounded {
        if (!request.getCommandToExecute().getUser().equals(response.getUser())) {
            Optional<ServerAccount> optServerAccount = accountRepository.get(response.getUser().getLogin());
            if (optServerAccount.isEmpty()) {
                throw new NoAccountFounded();
            } else {
                ServerAccount serverAccount = optServerAccount.get();
                if (serverAccount.getPassword().equals(response.getUser().getPassword())) {
                    this.setUser(serverAccount);
                } else {
                    throw new InvalidPassword();
                }
            }
        }
    }

}
