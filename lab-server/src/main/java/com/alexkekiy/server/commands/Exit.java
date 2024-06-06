package com.alexkekiy.server.commands;


import com.alexkekiy.common.data.Account;
import com.alexkekiy.common.utilites.Callable;
import com.alexkekiy.common.utilites.CommandType;
import com.alexkekiy.common.utilites.Response;
import com.alexkekiy.server.util.ServerCommand;

public class Exit extends ServerCommand implements Callable {
    public Exit() {
        super();
        this.setCommandType(CommandType.WITHOUT_ARGUMENTS);
    }

    public final String name = "exit";
    public static Exit staticFactory(String[] args,String value){
        Exit inst =  new Exit();
        inst.setValue(value);
        inst.setArgs(args);
        return  inst;
    };



    public Response calling(String[] args, String v, Account user)  {
        Response resp = super.calling(args, v,user);
        resp.setFlag(false);
        new Save().calling(args, v,user);
        return resp;

    }


}
