package com.kotsovskyi.domain;

public class Shot {
    public Shot (int x, int y) {
        this.x = x;
        this.y = y;
        hit = false;
    }

    public boolean getHit() {
        return hit;
    }

    public void setHit(int x, int y) {
        if((this.x == x) && (this.y == y)) {
            this.hit = true;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    private int x;
    private int y;
    private boolean hit;
}
