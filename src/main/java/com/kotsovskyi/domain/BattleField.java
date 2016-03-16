package com.kotsovskyi.domain;

public class BattleField {

    private Ship [] ships = new Ship[10];
    private Shot [] shots = new Shot[100];

    public BattleField (int [][] coordinates) {
        for (int i = 0; i < 10; i++) {
            ships[i] = new Ship(coordinates[i][0], coordinates[i][1], coordinates[i][2], coordinates[i][3], coordinates[i][4]);
        }

        int k = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                shots[k] = new Shot(i,j);
                k++;
            }
        }
    }

    public Ship [] getShips() {
        return ships;
    }

    public Shot [] getShots() {
        return shots;
    }

    public int [][] getShipCoordinates() {
        int [][] shipCoordinates = new int[10][6];
        for(int i = 0; i < 10; i++) {
            shipCoordinates[i] = ships[i].getShipCoordinates();
        }
        return shipCoordinates;
    }

    public boolean [] getHits() {
        boolean [] hits = new boolean[100];
        for(int i = 0; i < hits.length; i++) {
            hits[i] = shots[i].getHit();
        }
        return hits;
    }

    public int getIndexOfShot(int x, int y) {
        for (int i = 0; i < 100; i++) {
            if ( (x == shots[i].getX()) && (y == shots[i].getY()) ){
                return i;
            }
        }
        return 100;
    }

    public int getIndexOfShip(int x, int y) {
        for (int i = 0; i < 10; i++) {
            int xHead = ships[i].getXHead();
            int yHead = ships[i].getYHead();
            int xTail = ships[i].getXTail();
            int yTail = ships[i].getYTail();

            if ((xHead == xTail) && (xHead == x)) {
                for (int j = yHead; j <= yTail; j++) {
                    if (j == y) {
                        return i;
                    }
                }
            }
            if ((yHead == yTail) && (yHead == y)) {
                for (int j = xHead; j <= xTail; j++) {
                    if (j == x) {
                        return i;
                    }
                }
            }
        }
        return -1;
    }
}
