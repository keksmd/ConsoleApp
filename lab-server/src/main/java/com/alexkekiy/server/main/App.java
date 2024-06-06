package com.alexkekiy.server.main;


import com.alexkekiy.common.data.Account;
import com.alexkekiy.server.data.entities.ServerAccount;
import com.alexkekiy.server.main.handlers.ClientConnector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;

import static com.alexkekiy.server.data.repositories.ServerAccountRepository.getServerAccountRepository;
import static java.nio.channels.SelectionKey.OP_READ;
/**
 * main-класс,принимающий подключения и задающий конфигурации
 */
public class App {
    private static final int PORT = 8081;
    public static final Logger log = LogManager.getLogger(App.class.getName());


    private static ServerSocketChannel serverSocketChannel;
    private static Selector appSelector ;
    static {
        try {
            serverSocketChannel = ServerSocketChannel.open();
            appSelector= Selector.open();
            serverSocketChannel.bind(new InetSocketAddress(PORT));
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(appSelector, SelectionKey.OP_ACCEPT);
        }catch (IOException e){
            log.error(e);
        }
    }

    private App() {
    }

        public static void main(String[] args) {
            ServerAccount acc = new ServerAccount(Account.getCommonAcc().getLogin(), Account.getCommonAcc().getPassword());
            ServerAccount.commonAcc = acc;
            getServerAccountRepository().add(acc);

        try {
            System.out.println("Программа запущенна");

            while (true) {
                appSelector.select();
                System.out.println("мощность итератора по общему селетору = "+ appSelector.selectedKeys().size());
                Iterator<SelectionKey> keysIterator = appSelector.selectedKeys().iterator();
                    while (keysIterator.hasNext()) {
                        handleAcception();
                        keysIterator.next();
                    }
            }
        } catch (Exception e) {
            log.error("ошибка, приложение чуть не упало", e);
        }
    }
    private static void handleAcception() throws IOException{
        Selector oneCleintSelector = Selector.open();
        ClientConnector clientConnector = new ClientConnector(oneCleintSelector);
        clientConnector.setClientChannel(serverSocketChannel.accept());
        clientConnector.getClientManager().getChannel().configureBlocking(false);
        clientConnector.getClientChannel().register(oneCleintSelector, OP_READ);

        System.out.println("Соединение с клиентом обработано и передано конннектору для работы с клиентом");
        clientConnector.start();
    }



}