package com.alexkekiy.server.commands;


import com.alexkekiy.common.data.Account;
import com.alexkekiy.common.utilites.Callable;
import com.alexkekiy.common.utilites.CommandType;
import com.alexkekiy.common.utilites.Response;
import com.alexkekiy.server.data.entities.SpaceMarine;
import com.alexkekiy.server.util.ServerCommand;

import java.util.Comparator;
import java.util.Objects;

import static com.alexkekiy.server.main.managers.CollectionManager.getCollectionManager;

public class AddIfMax extends ServerCommand implements Callable {
    public AddIfMax() {
        super();
        this.setCommandType(CommandType.ELEMENT_ARGUMENTED);
    }
    public final String name = "add_if_max";
    public static AddIfMax staticFactory(String[] args,String value){
        AddIfMax inst =  new AddIfMax();
        inst.setValue(value);
        inst.setArgs(args);
        return  inst;
    };
@Override
    public Response calling(String[] args, String v, Account user)  {
        Response resp = super.calling(args, v,user);
        SpaceMarine spm = SpaceMarine.newInstance(args);
        if (spm.compareTo(Objects.requireNonNull(getCollectionManager().getCollectionStream().max(Comparator.naturalOrder()).orElse(null))) >= 0) {
            new Add().calling(args, v,user,spm);
        } else {
            resp.setSuccess(false);
        }
        return resp;
    }
}
