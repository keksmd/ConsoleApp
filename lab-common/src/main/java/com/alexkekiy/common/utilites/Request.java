package com.alexkekiy.common.utilites;

import com.alexkekiy.common.exceptions.NoAccountFounded;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
/**
 * дата-класс-обертка для запроса выполнения команды
 */
public class Request extends Message {
    private Command commandToExecute;
    public Response calling() throws NoAccountFounded {
        return commandToExecute.calling(this.commandToExecute.getArgs(), this.commandToExecute.getValue(), this.commandToExecute.getUser());
    }


}
