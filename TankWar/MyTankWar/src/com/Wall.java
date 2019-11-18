package com;

import java.awt.*;

/**
 * @author Chan
 * @version 1.0
 * @date 2019/10/29 18:39
 */
public class Wall {
    int x, y, width, height;

    Wall(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void draw(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.cyan);
        g.fillRect(x, y, width, height);
        g.setColor(c);
    }

    public Rectangle getRect() {
        return new Rectangle(x, y, width, height);
    }
}
