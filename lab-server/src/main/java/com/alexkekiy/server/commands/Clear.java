package com.alexkekiy.server.commands;


import com.alexkekiy.common.data.Account;
import com.alexkekiy.common.utilites.Callable;
import com.alexkekiy.common.utilites.CommandType;
import com.alexkekiy.common.utilites.Response;
import com.alexkekiy.server.util.ServerCommand;

import static com.alexkekiy.server.main.managers.CollectionManager.getCollectionManager;

public class Clear extends ServerCommand implements Callable {


    public Clear() {
        super();
        this.setCommandType(CommandType.WITHOUT_ARGUMENTS);
    }
    public final String name = "clear";
    public static Clear staticFactory(String[] args,String value){
        Clear inst =  new Clear();
        inst.setValue(value);
        inst.setArgs(args);
        return  inst;
    };

@Override
    public Response calling(String[] args, String v, Account user)  {
        Response resp = super.calling(args, v,user);
        getCollectionManager().getCollectionStream().forEach(w->{
            if(w.getUser_id().equals(user)) {
                getCollectionManager().remove(w);
                this.getUser().getDataBaseManager().removeById(w.getId());
            }
        });
        return resp;
    }
}

