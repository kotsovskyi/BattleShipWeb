package com.kotsovskyi.server;

public class FightRoom extends Thread {
    private static int roomNumber = 0;
    private Client cl1;
    private Client cl2;

    public FightRoom(Client cl1, Client cl2) {
        roomNumber++;
        this.cl1 = cl1;
        this.cl2 = cl2;
    }

    @Override
    public void run() {
        while(!cl1.isBattleshipsOnField() || !cl2.isBattleshipsOnField()) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public Client getCl1() {
        return cl1;
    }

    public Client getCl2() {
        return cl2;
    }
}


