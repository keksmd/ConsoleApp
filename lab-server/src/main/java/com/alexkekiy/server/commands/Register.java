package com.alexkekiy.server.commands;


import com.alexkekiy.common.data.Account;
import com.alexkekiy.common.utilites.Callable;
import com.alexkekiy.common.utilites.CommandType;
import com.alexkekiy.common.utilites.Response;
import com.alexkekiy.server.data.repositories.ServerAccountRepository;
import com.alexkekiy.server.data.entities.ServerAccount;
import com.alexkekiy.server.util.ServerCommand;

public class Register extends ServerCommand implements Callable {
    public Register() {
        super();
        this.setCommandType(CommandType.VALUE_ARGUMENTED);

    }

    public final String name = "register";



    public static Register staticFactory(String[] args, String value){
        Register inst =  new Register();
        inst.setValue(value);
        inst.setArgs(args);
        return  inst;
    };


    public Response calling(String[] args, String v, Account user)  {
        Response resp = super.calling(args, v,user);
            resp.setUser(new Account(this.getValue().split(";")[0], this.getValue().split(";")[1]));
        ServerAccountRepository.getServerAccountRepository().add(new ServerAccount(resp.getUser().getLogin(),resp.getUser().getPassword()));
            resp.addMessage("Вы успешно зарегестрировались и вошли");
            resp.setSuccess(true);
            return resp;

    }

}
