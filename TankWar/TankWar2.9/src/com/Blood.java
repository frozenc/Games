package com;

import java.awt.*;

/**
 * @author Chan
 * @version 1.0
 * @date 2019/10/22 21:18
 */
public class Blood {
    int x, y, w, h;
    TankClient tc;

    int step = 0;
    private boolean live = true;

    public Blood() {
        x = pos[0][0];
        y = pos[0][1];
        w = h = 15;
    }

    private int[][] pos = {
        {350, 300}, {360, 300}, {375, 275}, {400, 200}, {360, 270}, {365, 290}, {340, 280}
    };

    public void draw(Graphics g) {
        if(!this.isLive()) return;
        Color c = g.getColor();
        g.setColor(Color.MAGENTA);
        g.fillRect(x, y, w, h);

        move();
    }

    private void move() {
        step ++;
        if(step == pos.length) {
            step = 0;
        }
        x = pos[step][0];
        y = pos[step][1];
    }

    public Rectangle getRect() {
        return new Rectangle(x, y, w, h);
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }
}
