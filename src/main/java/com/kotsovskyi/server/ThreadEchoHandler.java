package com.kotsovskyi.server;

import com.kotsovskyi.utils.FightRoomDirectory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Класс реализует интерфейс Runnable и в свое методе run() поддерживает взаимодействие с программой клиентом
 * Программа клиент передает серверу строку, содержащую URL ресурса в сети. Сервер возвращает клиенту
 * содержимое ресурса
 */

public class ThreadEchoHandler extends Thread {
    private Socket client;
    private static PrintStream outputStream; //передача
    private static BufferedReader inputStream; //чтение
    private InetAddress address; //адрес клиєнта

    private static Queue<Client> applicationForBattle = new ArrayDeque<Client>();

    public ThreadEchoHandler(Socket s) throws IOException {
        client = s;
        outputStream = new PrintStream(s.getOutputStream());
        inputStream = new BufferedReader(new InputStreamReader(s.getInputStream()));
        address = s.getInetAddress();

    }

    @Override
    public void run() {
        String str;
        try {
            while((str = inputStream.readLine()) != null) {
                if("newApplication".equals(str)) {
                    if(applicationForBattle.size() >= 2) {
                            FightRoom fightRoom;
                            synchronized (this) {
                                // Створюємо кімнату-бій для двох користувачів
                                Client cl1 = applicationForBattle.poll();
                                Client cl2 = applicationForBattle.poll();
                                fightRoom = new FightRoom(cl1, cl2);

                                FightRoomDirectory.addFightRoom(cl1.getName(), fightRoom);
                                FightRoomDirectory.addFightRoom(cl2.getName(), fightRoom);
                                outputStream.println("new FightRoom created !!!!");
                        }
                        fightRoom.start();
                    }
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
        Server.numberOfOnline--; // уменьшает число клиентов онлайн при отсоединении клиента
        System.out.println("There are " + Server.numberOfOnline + " clients online");
        disconnect(); // уничтожение потока
    }

    private void disconnect() {
        try {
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            this.interrupt();
        }
    }

    public static void addApplicationForBattle(Client client) {
        applicationForBattle.add(client);
    }
}
