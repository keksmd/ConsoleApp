package com.alexkekiy.server.commands;


import com.alexkekiy.common.data.Account;
import com.alexkekiy.common.utilites.Callable;
import com.alexkekiy.common.utilites.CommandType;
import com.alexkekiy.common.utilites.Response;
import com.alexkekiy.server.data.entities.SpaceMarine;
import com.alexkekiy.server.main.managers.CollectionManager;
import com.alexkekiy.server.util.ServerCommand;

public class UpdateById extends ServerCommand implements Callable {
    public UpdateById() {
        super();
        this.setCommandType(CommandType.ELEMENT_AND_VALUE_ARGUMENTED);
    }
    public final String name = "update_by_id";
    public static UpdateById staticFactory(String[] args,String value){
        UpdateById inst =  new UpdateById();
        inst.setValue(value);
        inst.setArgs(args);
        return  inst;
    };



    public Response calling(String[] args, String v, Account user)  {
        Response resp = super.calling(args, v,user);
        if (CollectionManager.getCollectionManager().getCollectionStream().anyMatch(w -> String.valueOf(w.getId()).equals(this.getValue()))) {
            CollectionManager.getCollectionManager().getCollectionStream().filter(w -> String.valueOf(w.getId()).equals(this.getValue())).forEach(w->{
                if(w.getUser_id().equals(user)){
                    SpaceMarine spm= CollectionManager.getCollectionManager().getCollectionStream().filter(x -> x.getId() == Integer.parseInt(this.getValue())).findFirst().get();
                    spm.update(this.getArgs());
                    this.getUser().getDataBaseManager().updateSpaceMarine(spm,this.getArgs());
                    resp.addMessage("Объект успешно обновлен");
                }else{
                    resp.addMessage("Объект с этим id принадлежит не вам");
                    resp.setSuccess(false);
                }
            });
        } else {
            resp.addMessage("Ошибка, не существует элемента с таким ID");
            resp.setSuccess(false);
        }
        return resp;
    }


}
