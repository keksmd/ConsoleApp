package com.alexkekiy.server.commands;


import com.alexkekiy.common.data.Account;
import com.alexkekiy.common.utilites.Callable;
import com.alexkekiy.common.utilites.CommandType;
import com.alexkekiy.common.utilites.Response;
import com.alexkekiy.server.util.ServerCommand;

public class Enter extends ServerCommand implements Callable {
    public Enter() {
        super();
        this.setCommandType(CommandType.VALUE_ARGUMENTED);

    }

    public final String name = "enter";



    public static Enter staticFactory(String[] args, String value){
        Enter inst =  new Enter();
        inst.setValue(value);
        inst.setArgs(args);
        return  inst;
    };
@Override

    public Response calling(String[] args, String v, Account user) {
        Response resp = super.calling(args, v,user);
            resp.setUser(new Account(this.getValue().split(";")[0], this.getValue().split(";")[1]));
            resp.addMessage("Вы успешно вошли");

        return resp;

    }

}
