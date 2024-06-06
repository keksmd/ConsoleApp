package com.alexkekiy.server.commands;

import com.alexkekiy.common.data.Account;
import com.alexkekiy.common.utilites.Callable;
import com.alexkekiy.common.utilites.CommandType;
import com.alexkekiy.common.utilites.Response;
import com.alexkekiy.server.data.entities.SpaceMarine;
import com.alexkekiy.server.main.managers.CollectionManager;
import com.alexkekiy.server.util.ServerCommand;

public class PrintFieldDescendingLoyal extends ServerCommand implements Callable {
    public PrintFieldDescendingLoyal() {
        super();
        this.setCommandType(CommandType.WITHOUT_ARGUMENTS);
    }

    public final String name = "print_field_descending_loyal";
    public static PrintFieldDescendingLoyal staticFactory(String[] args,String value){
        PrintFieldDescendingLoyal inst =  new PrintFieldDescendingLoyal();
        inst.setValue(value);
        inst.setArgs(args);
        return  inst;
    };



    public Response calling(String[] args, String v, Account user)  {
        Response resp = super.calling(args, v,user);
        StringBuilder s = new StringBuilder();
        CollectionManager.getCollectionManager().getCollectionStream().filter(SpaceMarine::getLoyal).forEach(s::append);
        CollectionManager.getCollectionManager().getCollectionStream().filter(w -> !w.getLoyal()).forEach(s::append);
        resp.addMessage(s.toString());
        return resp;

    }
}
