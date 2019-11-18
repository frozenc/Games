package com;

import java.awt.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author Chan
 * @version 1.0
 * @date 2019/10/29 20:07
 */
public class Blood {
    int x, y, width, height;

    boolean live = true;
    int step;
    private int[][] pos = {
            {350, 300}, {360, 300}, {375, 275}, {400, 200}, {360, 270}, {365, 290}, {340, 280}
    };

    Blood() {
        this.x = pos[0][0];
        this.y = pos[0][1];
        this.width = 15;
        this.height = 15;
    }

    public Rectangle getRect() {
        return new Rectangle(x, y, width, height);
    }

    public void move() {
        if(step == pos.length) step = 0;
        x = pos[step][0];
        y = pos[step][1];
        step ++;
    }

    public void draw(Graphics g) {
        if(!this.isLive()) return;

        Color c = g.getColor();
        g.setColor(Color.RED);
        g.fillOval(x, y, width, height);
        g.setColor(c);

        move();
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }
}
