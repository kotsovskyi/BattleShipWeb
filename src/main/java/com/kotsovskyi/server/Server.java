package com.kotsovskyi.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable{
    static private final int port = 3456;
    static public int numberOfOnline = 0;
    private ServerSocket server;
    private Socket client;

    private static Server instance;

    private Server() {
    }

    public static Server getInstance() {
        if(instance == null) {
            instance = new Server();

            Runnable r = instance;
            Thread t = new Thread(r);
            t.start();

        }
        return instance;
    }

    @Override
    public void run() {
        try {
            server = new ServerSocket(port, 100);

            while(true) {
                try {
                    client = server.accept();
                } catch (IOException e) {
                    System.out.println("Ошибка при подключении к порту: " + port);
                    System.exit(-1);
                }
                numberOfOnline++;
                System.out.println("One more client has been connected");
                System.out.println("There are " + Server.numberOfOnline + " clients online");

                ThreadEchoHandler threadHandler = new ThreadEchoHandler(client);
                threadHandler.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                server.close();
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
