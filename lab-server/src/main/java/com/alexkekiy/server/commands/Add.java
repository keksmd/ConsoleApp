package com.alexkekiy.server.commands;
import com.alexkekiy.common.data.Account;
import com.alexkekiy.common.utilites.Callable;
import com.alexkekiy.common.utilites.CommandType;
import com.alexkekiy.common.utilites.Response;
import com.alexkekiy.server.data.entities.ServerAccount;
import com.alexkekiy.server.data.entities.SpaceMarine;
import com.alexkekiy.server.main.managers.CollectionManager;
import com.alexkekiy.server.util.ServerCommand;
public class Add extends ServerCommand implements Callable {
    public final String name = "add";
    public Add() {
        super();
        this.setCommandType(CommandType.ELEMENT_ARGUMENTED);
    }
    public static Add staticFactory(String[] args,String value){
        Add inst =  new Add();
        inst.setValue(value);
        inst.setArgs(args);
        return  inst;
    }
    public Response calling(String[] args, String v,Account user,SpaceMarine spm)  {
        Response resp = super.calling(args,v,user);
        spm.setUser_id((ServerAccount)resp.getUser());
        CollectionManager.getCollectionManager().add(spm);
        this.getUser().getDataBaseManager().add(spm);
        return resp;

    }@Override
    public Response calling(String[] args, String v, Account user)  {
        SpaceMarine spm = SpaceMarine.newInstance(args);
        return this.calling(args,v,user,spm);
    }
}
