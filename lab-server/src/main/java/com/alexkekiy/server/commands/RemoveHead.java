package com.alexkekiy.server.commands;


import com.alexkekiy.common.data.Account;
import com.alexkekiy.common.utilites.Callable;
import com.alexkekiy.common.utilites.CommandType;
import com.alexkekiy.common.utilites.Response;
import com.alexkekiy.server.data.entities.SpaceMarine;
import com.alexkekiy.server.main.managers.CollectionManager;
import com.alexkekiy.server.util.ServerCommand;

public class RemoveHead extends ServerCommand implements Callable {
    public RemoveHead() {
        super();
        this.setCommandType(CommandType.WITHOUT_ARGUMENTS);
    }
    public final String name = "remove_head";
    public static RemoveHead staticFactory(String[] args,String value){
        RemoveHead inst =  new RemoveHead();
        inst.setValue(value);
        inst.setArgs(args);
        return  inst;
    };



    public Response calling(String[] args, String v, Account user)  {
        Response resp = super.calling(args, v,user);
        if(CollectionManager.getCollectionManager().peek()!=null&& CollectionManager.getCollectionManager().peek().getUser_id().equals(user)){
            SpaceMarine spm = CollectionManager.getCollectionManager().poll();
            this.getUser().getDataBaseManager().removeById(spm.getId());
            resp.addMessage("голова успешно удалена");
        }else{
            resp.addMessage(" невозможно удалить голову(очередь пуста или голова не принадлежит вам)");
        }
        return resp;
    }
}
