package com.alexkekiy.server.commands;

import com.alexkekiy.common.data.Account;
import com.alexkekiy.common.utilites.Callable;
import com.alexkekiy.common.utilites.CommandType;
import com.alexkekiy.common.utilites.Response;
import com.alexkekiy.server.main.managers.CollectionManager;
import com.alexkekiy.server.util.ServerCommand;

public class Show extends ServerCommand implements Callable {
    public Show() {
        super();
        this.setCommandType(CommandType.WITHOUT_ARGUMENTS);
    }
    public final String name = "show";
    public static Show staticFactory(String[] args,String value){
        Show inst =  new Show();
        inst.setValue(value);
        inst.setArgs(args);
        return  inst;
    };
    public Response calling(String[] args, String v, Account user)  {
        Response resp = super.calling(args, v,user);

        if (CollectionManager.getCollectionManager().getCollectionSize()==0) {
            resp.addMessage("В коллекции нет элементов");
        }

        CollectionManager.getCollectionManager().getCollectionStream().forEach(
                w -> resp.addMessage(w + "\n"));
        return resp;
    }
}
