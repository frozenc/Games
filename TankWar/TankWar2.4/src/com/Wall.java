package com;

import java.awt.*;

/**
 * @author Chan
 * @version 1.0
 * @date 2019/10/22 19:32
 */
public class Wall {
    int x, y, w, h;
    TankClient tc;

    public Wall(int x, int y, int w, int h, TankClient tc) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.tc = tc;
    }

    public void draw(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.cyan);
        g.fillRect(x, y, w, h);
        g.setColor(c);
    }

    public Rectangle getRect() {
        return new Rectangle(x, y, w, h);
    }
}
