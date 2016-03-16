package com.kotsovskyi.domain;

public class Ship {

    private int xHead;
    private int yHead;

    private int xTail;
    private int yTail;

    private int length;
    private int wounds;

    public Ship(int xHead, int yHead, int xTail, int yTail, int length) {
        this.xHead = xHead;
        this.yHead = yHead;

        this.xTail = xTail;
        this.yTail = yTail;

        this.length = length;
        wounds = 0;
    }

    public int getXHead() {
        return xHead;
    }

    public int getYHead() {
        return yHead;
    }

    public int getXTail() {
        return xTail;
    }

    public int getYTail() {
        return yTail;
    }

    public int getLength() {
        return length;
    }

    public int getWounds() {
        return wounds;
    }

    public int [] getShipCoordinates() {
        int [] shipCoordinates = {xHead,
                                  yHead,
                                  xTail,
                                  yTail,
                                  length,
                                  wounds};
        return shipCoordinates;
    }

    public void setWound() {
        wounds++;
    }
}
