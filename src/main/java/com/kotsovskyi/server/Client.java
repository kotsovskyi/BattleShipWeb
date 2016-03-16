package com.kotsovskyi.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;

public class Client implements Runnable {
    private String name;
    private boolean battleshipsOnField;
    private boolean attacker;

    private static Socket client;
    private static final int port = 3456;
    private static PrintStream outputStream;
    private static BufferedReader inputStream;

    public Client(String name) {
        battleshipsOnField = false;
        this.name = name;

        try {
            client = new Socket(InetAddress.getByName("127.0.0.1"), port);
            outputStream = new PrintStream(client.getOutputStream());
            inputStream = new BufferedReader(
                    new InputStreamReader(client.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        outputStream.println("newApplication");
    }

    @Override
    public void run() {
        try {
            System.out.println(inputStream.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public void setBattleshipsOnField(){
        battleshipsOnField = true;
    }

    public boolean isBattleshipsOnField() {
        return battleshipsOnField;
    }

    public boolean isAttacker() {
        return attacker;
    }

    public void setAttacker(boolean attacker) {
        this.attacker = attacker;
    }
}
