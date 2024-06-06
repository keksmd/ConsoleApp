/**
 * Класс реализует паттерн command, и служит
 * для вызова разных команд в зависимости от того,что было введено в консоль
 */
package com.alexkekiy.server.util;


import com.alexkekiy.common.data.Account;
import com.alexkekiy.common.exceptions.NoAccountFounded;
import com.alexkekiy.common.utilites.Command;
import com.alexkekiy.common.utilites.CommandType;
import com.alexkekiy.common.utilites.Response;
import com.alexkekiy.server.data.entities.ServerAccount;
import com.alexkekiy.server.data.repositories.ServerAccountRepository;
import lombok.Setter;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

import static com.alexkekiy.common.utilites.JsonSerializer.toJson;

/**
 *
 * реализация команды на сервере,аккаунт для исполнения у которой должен быть синхронизирован с бд
 */
@Setter
public class ServerCommand extends Command{




    public Response calling(String[] a, String v, Account user){
        return super.calling(a,v,user);
    }
    public Response calling(){
        return super.calling();
    }
    public ServerCommand() {
    }
    public CommandType commandType;

    public ServerCommand(String[] args, String value) {
        super(args, value);
    }
    private ServerAccount user;

    @Override
    public ServerAccount getUser() {
        return user;
    }







    public static ServerCommand castInto(ServerCommand command1,Command command) {
        ServerCommand answer;
        try {
            answer = command1.getClass().getConstructor().newInstance();
            answer.setArgs(command.getArgs());
            answer.setName(command.getName());
            answer.setValue(command.getValue());
            System.out.println(toJson(command.getUser()));
            Optional<ServerAccount> optionalServerAccount = ServerAccountRepository.getServerAccountRepository().get(command.getUser().getLogin());
            if(optionalServerAccount.isPresent()){
                answer.setUser(optionalServerAccount.get());
            }else{
                System.out.println("не нашлось ни одного пользователя с именем "+command.getUser().getLogin());
                throw new NoAccountFounded();
            }

        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException |
                 NoAccountFounded e) {
            throw new RuntimeException(e);
        }
        return answer;
    }
}
