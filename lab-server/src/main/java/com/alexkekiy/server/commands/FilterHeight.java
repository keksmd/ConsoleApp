package com.alexkekiy.server.commands;


import com.alexkekiy.common.data.Account;
import com.alexkekiy.common.utilites.Callable;
import com.alexkekiy.common.utilites.CommandType;
import com.alexkekiy.common.utilites.Response;
import com.alexkekiy.server.main.managers.CollectionManager;
import com.alexkekiy.server.util.ServerCommand;

public class FilterHeight extends ServerCommand implements Callable {
    public FilterHeight() {
        super();
        this.setCommandType(CommandType.VALUE_ARGUMENTED);
    }
    public final String name = "filter_greater_than_height";
    public static FilterHeight staticFactory(String[] args,String value){
        FilterHeight inst =  new FilterHeight();
        inst.setValue(value);
        inst.setArgs(args);
        return  inst;
    };



    public Response calling(String[] args, String v, Account user)  {
        Response resp = super.calling(args, v,user);
        StringBuilder s = new StringBuilder();
        CollectionManager.getCollectionManager().getCollectionStream().filter(w->w.getUser_id().getLogin().equals(user.getLogin())).filter(w -> w.getHeight() > Integer.parseInt(getValue())).forEach(s::append);
        resp.addMessage(s.toString());
        return resp;
    }
}
