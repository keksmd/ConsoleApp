package com.alexkekiy.server.main.handlers;

import com.alexkekiy.common.exceptions.MessageWasNotReadedSuccessfull;
import com.alexkekiy.common.utilites.Request;

import java.io.IOException;
import java.util.concurrent.RecursiveAction;

import static com.alexkekiy.server.ServerMessaging.readRequest;
import static com.alexkekiy.server.main.App.log;

/**
 * класс,считывающий запросы c помощью Executor-а
 */
public class RequestReader extends RecursiveAction {
    private final ClientConnector client;


    public RequestReader(ClientConnector client) {
        this.client = client;
    }

    @Override
    protected void compute()  {
        Request request = null;
        try {
            request = readRequest(client.getClientChannel());
        } catch (IOException e) {
            log.error("непрочитали", e);
        } catch (MessageWasNotReadedSuccessfull e) {

        }
        if (request != null) {
            System.out.println("прочитали: " + request);
            new RequestHandler(request,client).run();

        }
    }
}
