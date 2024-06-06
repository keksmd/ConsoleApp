package com.alexkekiy.server.util;

import com.alexkekiy.common.utilites.Command;
import com.alexkekiy.server.commands.NotFound;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import static com.alexkekiy.server.util.ServerCommand.castInto;

public class CommandExtractor {
    public static HashMap<String, Method> nameToHandleMap = new HashMap<>();

    /**
     * Метод, возвращающий команду,определяемую по текстовому запросу
     *
     * @param str - текстовое значение команды
     * @return объект, реализующий команду
     */
    public static ServerCommand extractCommand (String str) throws InvocationTargetException, IllegalAccessException {
        String[] tokens = str.split(" ");
        String prefix = "";
        for(int i = 0;i< tokens.length;i++){
            prefix+=tokens[i];
            if(nameToHandleMap.containsKey(prefix)) {
                Method factory = nameToHandleMap.get(prefix);
                if (i < tokens.length - 1) {

                        return (ServerCommand)factory.invoke(null, tokens[i + 1], null);

                }else{
                    return (ServerCommand) factory.invoke(null, null, null);
                }
            }
            prefix+=" ";
        }
        return new NotFound();

    }

    public static ServerCommand mapCommand(Command command, String name) throws InvocationTargetException, IllegalAccessException {
        ServerCommand s =  extractCommand(name);
        return castInto(s,command);
    }
}
