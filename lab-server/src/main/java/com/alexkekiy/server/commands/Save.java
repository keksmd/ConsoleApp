package com.alexkekiy.server.commands;


import com.alexkekiy.common.data.Account;
import com.alexkekiy.common.utilites.CommandType;
import com.alexkekiy.common.utilites.Response;
import com.alexkekiy.server.util.ServerCommand;



public class Save extends ServerCommand{
    public Save() {
        super();
        this.setCommandType(CommandType.WITHOUT_ARGUMENTS);
    }
    public final String name = "save";
    public static Save staticFactory(String[] args,String value){
        Save inst =  new Save();
        inst.setValue(value);
        inst.setArgs(args);
        return  inst;
    };



    public Response calling(String[] args, String v, Account user) {
        Response resp = super.calling(args, v,user);
        return resp;
    }

}