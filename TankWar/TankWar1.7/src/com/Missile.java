package com;


import java.awt.*;

/**
 * @author Chan
 * @version 1.0
 * @date 2019/10/21 22:10
 */
public class Missile {
    private int x,y;
    Tank.Direction dir;
    public static final int MISSILE_WIDTH = 10;
    public static final int MISSILE_HEIGHT = 10;
    private static final int XSPEED = 10;
    private static final int YSPEED = 10;

    private boolean live = true;

    public TankClient tc = null;

    public Missile(int x, int y, Tank.Direction dir) {
        this.x = x;
        this.y = y;
        this.dir = dir;
    }

    public Missile(int x, int y, Tank.Direction dir, TankClient tc) {
        this(x, y, dir);
        this.tc = tc;
    }

    public void draw(Graphics g) {
        if(!this.isLive()) {
            tc.missiles.remove(this);
            return;
        }
        Color c = g.getColor();
        g.setColor(Color.BLACK);
        g.fillOval(x, y, MISSILE_WIDTH, MISSILE_HEIGHT);
        g.setColor(c);

        move();
    }

    private void move() {
        switch (dir) {
            case L:
                x -= XSPEED;
                break;
            case LU:
                x -= XSPEED;
                y -= YSPEED;
                break;
            case U:
                y -= YSPEED;
                break;
            case RU:
                x += XSPEED;
                y -= YSPEED;
                break;
            case R:
                x += XSPEED;
                break;
            case RD:
                x += XSPEED;
                y += YSPEED;
                break;
            case D:
                y += YSPEED;
                break;
            case LD:
                x -= XSPEED;
                y += YSPEED;
                break;
        }

        if(x < 0 || y < 0 || x > TankClient.GAME_WIDTH || y > TankClient.GAME_HEIGHT) {
            live = false;
        }
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public Rectangle getRect() {
        return new Rectangle(x, y, MISSILE_WIDTH, MISSILE_HEIGHT);
    }

    public boolean hitTank(Tank t) {
        if(this.getRect().intersects(t.getRect()) && t.isLive()) {
            t.setLive(false);
            this.setLive(false);
            Explode e = new Explode(x, y, tc);
            tc.explodes.add(e);
            return true;
        }
        return false;
    }
}
