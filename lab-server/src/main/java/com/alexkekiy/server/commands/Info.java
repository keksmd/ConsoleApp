package com.alexkekiy.server.commands;


import com.alexkekiy.common.data.Account;
import com.alexkekiy.common.utilites.Callable;
import com.alexkekiy.common.utilites.CommandType;
import com.alexkekiy.common.utilites.Response;
import com.alexkekiy.server.util.ServerCommand;

import static com.alexkekiy.server.main.managers.CollectionManager.getCollectionManager;

public class Info extends ServerCommand implements Callable {
    public Info() {
        super();
        this.setCommandType(CommandType.WITHOUT_ARGUMENTS);
    }
    public final String name = "info";
    public static Info staticFactory(String[] args,String value){
        Info inst =  new Info();
        inst.setValue(value);
        inst.setArgs(args);
        return  inst;
    };


    public Response calling(String[] args, String v, Account user)  {
        Response resp = super.calling(args, v,user);
        resp.addMessage(String.format("В коллекции хранится %d элементов, %d из них принадлежат вам",  getCollectionManager().getCollectionSize(),getCollectionManager().getCollectionStream().filter(w -> w.getUser_id().equals(user)).count()));
        return resp;
    }

}
