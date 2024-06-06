package com.alexkekiy.server.main.handlers;

import com.alexkekiy.common.exceptions.NoAccountFounded;
import com.alexkekiy.common.utilites.Request;
import com.alexkekiy.common.utilites.Response;
import com.alexkekiy.server.exceptions.InvalidPassword;

import java.lang.reflect.InvocationTargetException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.alexkekiy.server.util.CommandExtractor.mapCommand;

/**
 * класс,обрабатывающий запрос в отдельном потоке и создающий ответное сообщение
 */

public class RequestHandler implements Runnable {
    private final Request request;
    private final ClientConnector connector;

    public RequestHandler(Request request,ClientConnector connector) {
        this.connector= connector;
        this.request = request;
    }

    @Override
    public void run() {
        if (request != null) {
            if (request.getMessages().get(0).equals("commands") && connector.getClientManager().isFirstMessageFromClient()) {
                connector.setCommands();
            } else {
                try {
                    Response response = null;
                    try {
                        request.setCommandToExecute(mapCommand(request.getCommandToExecute(), request.getMessages().get(0)));
                        response= request.calling();
                        System.out.println("calling прошел");
                        connector.getClientManager().checkAnswer(response,request);
                    } catch (NoAccountFounded e) {
                        response = (response==null)?new Response():response;
                        response.setSuccess(false);
                        response.setMessages(Stream.of("Аккаунт не найден").collect(Collectors.toCollection(ArrayList::new)));
                    } catch (InvalidPassword e) {
                        response.setSuccess(false);
                        response.setMessages(Stream.of("Неверный пароль").collect(Collectors.toCollection(ArrayList::new)));
                    }
                   connector.responses.add(response);
                } catch (InvocationTargetException | IllegalAccessException e) {
                    System.out.println("Ошибка: неверно реализована команда");
                    e.printStackTrace();
                }
            }
            try {
                connector.getClientChannel().register(connector.getSelector(), SelectionKey.OP_WRITE);
                System.out.println("зарегали на write");
            } catch (ClosedChannelException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
