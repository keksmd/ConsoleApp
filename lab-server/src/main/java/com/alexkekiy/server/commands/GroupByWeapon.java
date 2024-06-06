package com.alexkekiy.server.commands;


import com.alexkekiy.common.data.Account;
import com.alexkekiy.common.utilites.Callable;
import com.alexkekiy.common.utilites.CommandType;
import com.alexkekiy.common.utilites.Response;
import com.alexkekiy.server.data.entities.Weapon;
import com.alexkekiy.server.main.managers.CollectionManager;
import com.alexkekiy.server.util.ServerCommand;

import java.util.Arrays;


public class GroupByWeapon extends ServerCommand implements Callable {
    public GroupByWeapon() {
        super();
        this.setCommandType(CommandType.WITHOUT_ARGUMENTS);
    }
    public final String name = "group_counting_by_weapon_type";
    public static GroupByWeapon staticFactory(String[] args,String value){
        GroupByWeapon inst =  new GroupByWeapon();
        inst.setValue(value);
        inst.setArgs(args);
        return  inst;
    };



    public Response calling(String[] args, String v, Account user)  {
        Response resp = super.calling(args, v,user);
        StringBuilder s = new StringBuilder();
        Arrays.stream(Weapon.values()).forEach(gun -> s.append(String.format("%s : %d%n", gun.name(), CollectionManager.getCollectionManager().getCollectionStream().filter(w -> w.getWeaponType() == gun).count())));
        resp.addMessage(s.toString());
        return resp;
    }

}
