package com.alexkekiy.server.commands;


import com.alexkekiy.common.data.Account;
import com.alexkekiy.common.utilites.Callable;
import com.alexkekiy.common.utilites.CommandType;
import com.alexkekiy.common.utilites.Response;
import com.alexkekiy.server.main.managers.CollectionManager;
import com.alexkekiy.server.util.ServerCommand;

public class RemoveById extends ServerCommand implements Callable {
    public RemoveById() {
        super();
        this.setCommandType(CommandType.VALUE_ARGUMENTED);
    }

    public final String name = "remove_by_id";
    public static RemoveById staticFactory(String[] args,String value){
        RemoveById inst =  new RemoveById();
        inst.setValue(value);
        inst.setArgs(args);
        return  inst;
    };



    public Response calling(String[] args, String v, Account user)  {
        Response resp = super.calling(args, v,user);
        if (CollectionManager.getCollectionManager().getCollectionStream().anyMatch(w -> String.valueOf(w.getId()).equals(this.getValue()))) {
            CollectionManager.getCollectionManager().getCollectionStream().filter(w -> String.valueOf(w.getId()).equals(this.getValue())).forEach(w->{
                if(w.getUser_id().equals(user)){
                    CollectionManager.getCollectionManager().remove(w);
                    this.getUser().getDataBaseManager().removeById(Integer.parseInt(this.getValue()));
                    resp.addMessage("Объект успешно удален");
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
