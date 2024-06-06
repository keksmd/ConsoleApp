package com.alexkekiy.server.main.handlers;

import com.alexkekiy.common.utilites.Response;

import java.io.IOException;

import static com.alexkekiy.server.ServerMessaging.sendResponse;

/**
 * класс задания отправки сообщения,используется с Executor - ом
 */
public class ResponseSender implements Runnable {
    private final Response response;
    private final ClientConnector connector;

    public ResponseSender(Response response, ClientConnector connector) {
        this.connector = connector;
        this.response = response;
    }

    @Override
    public void run() {
        try {
            sendResponse(connector.getClientChannel(), response);
            System.out.println("сообщение отправлено: " + response.getMessages().get(0));
        } catch (IOException e) {
            System.out.println("неудачно отправили сообщение");
            throw new RuntimeException(e);
        }
    }
}
