package com.alexkekiy.server.main.handlers;


import com.alexkekiy.common.utilites.Response;
import com.alexkekiy.server.commands.*;
import com.alexkekiy.server.main.managers.ClientManager;
import com.alexkekiy.server.util.CommandExtractor;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.SocketException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.stream.Stream;

import static com.alexkekiy.server.main.App.log;

/**
 * класс,управляющий подключением конкретного клиента к серверу,управялющий его аккаунтом,запросами и ответами
 */

public class ClientConnector extends Thread {
    private static final ForkJoinPool pool = new ForkJoinPool();
    private final static ExecutorService responseSendingPool = Executors.newFixedThreadPool(5);
    final LinkedBlockingDeque<Response> responses = new LinkedBlockingDeque<>(100);
    @Setter
    @Getter
    private Selector selector;
    @Getter
    private ClientManager clientManager;

    public ClientConnector(Selector selector) {
        this.selector = selector;
    }

    /**
     * Метод,задающий команды,которыми будут пользоваться клиент
     * и сервер
     */

    protected void setCommands() {
        this.clientManager.setFirstMessageFromClient(false);
        StringBuilder sb = new StringBuilder();
        Stream.of(Add.class, AddIfMax.class, AddIfMin.class, Help.class, Clear.class, Enter.class, Exit.class, FilterHeight.class, GroupByWeapon.class, Info.class, PrintFieldDescendingLoyal.class, Register.class, RemoveById.class, RemoveHead.class, Save.class, Show.class, UpdateById.class).
                forEach(w -> {
                    try {
                        CommandExtractor.nameToHandleMap.put(String.valueOf(w.getField("name").get(w.getConstructor().newInstance())), w.getMethod("staticFactory", String[].class, String.class));
                        sb.
                                append(w.getField("name").get(w.getConstructor().newInstance())).
                                append(",").
                                append(w.getField("commandType").get(w.getConstructor().newInstance()).toString()).
                                append(";");
                    } catch (IllegalAccessException | NoSuchFieldException |
                             NoSuchMethodException |
                             InstantiationException |
                             InvocationTargetException ignored) {
                    }
                });

        Response commandsResponse = new Response();
        commandsResponse.addMessage(sb.toString());
        responses.add(commandsResponse);
    }

    /**
     * Метод,запускающий коннектор
     */

    public void run() {
        try {
            while (true) {//true

                //this.getSelector().select();
                while (this.getSelector().selectNow() == 0) {
                }
                System.out.println("мощность итератора по селетору =" + getSelector().selectedKeys().size());
                Iterator<SelectionKey> keysIterator = this.getSelector().selectedKeys().iterator();
                try {
                    while (keysIterator.hasNext()) {
                        this.handleKey(keysIterator.next());
                        keysIterator.remove();
                    }
                } catch (SocketException | ClosedChannelException e) {
                    clientManager.getChannel().close();
                } catch (Exception e) {
                    log.error("ошибка,сервер чуть не лег", e);
                }
            }
        } catch (IOException ignored) {

        }

    }

    /**
     * метод, определяющий готовность ключа к тем или иным действиям, и вызывающий методы их реализации
     *
     * @param key обрабатываемый ключ
     * @throws IOException (пробрасывает выше) в случае ошибки при преме-передаче сообщений
     */
    private void handleKey(SelectionKey key) throws IOException {
        if (key.isWritable()) {

            this.handleWrite();
        } else if (key.isReadable()) {

            this.handleRead();
        }
    }


    /**
     * метод чтения сообщения ключа
     */

    private void handleRead() {
        RequestReader requestReader = new RequestReader(this);
        pool.execute(requestReader);

    }

    /**
     * метод отправки ответа
     */
    private void handleWrite() {
        try {
            Response response = this.responses.take();
            responseSendingPool.submit(new ResponseSender(response, this));

            try {
                this.getClientChannel().register(this.getSelector(), SelectionKey.OP_READ);
            } catch (ClosedChannelException ignored) {
            }


        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }


    public SocketChannel getClientChannel() {
        return this.clientManager.getChannel();
    }

    public void setClientChannel(SocketChannel clientChannel) {
        this.clientManager = new ClientManager(clientChannel);
    }

}
