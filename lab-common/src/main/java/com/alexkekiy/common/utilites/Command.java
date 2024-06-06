/**
 * Класс реализует паттерн command, и служит
 * для вызова разных команд в зависимости от того,что было введено в консоль
 */
package com.alexkekiy.common.utilites;


import com.alexkekiy.common.data.Account;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.lang.reflect.Field;

/**
 * абстрактный класс для команд
 */
@Setter
@Getter
@RequiredArgsConstructor
public abstract class Command implements Callable{
    /**
     * метод,реализующий взаимодействие с коллекцией
     */
    public Response calling(String[] a, String v, Account user)  {
        Response resp = new Response();
        resp.setUser(user);
        this.setUser(user);
        this.setValue(v);
        this.setArgs(a);
        resp.setSuccess(true);
        return resp;
    }
    public  Response calling(){
        return this.calling(this.args,this.value,this.user);
    }

    public Command(String[] args, String value) {
        this.args = args;
        this.value = value;
    }
    @Setter
    @Getter
    Account user;



    private String[] args;
    private String value;
    private String name = "command";








    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("***** ").append(this.getClass()).append(" Details *****\n");
        for (Field f : this.getClass().getFields()) {
            try {
                f.setAccessible(true);
                if (f.get(this) == null) {
                    s.append(f.getName()).append("=").append("null").append("\n");
                } else {
                    s.append(f.getName()).append("=").append(f.get(this).toString()).append("\n");
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        s.append("*****************************");

        return s.toString();
    }


}