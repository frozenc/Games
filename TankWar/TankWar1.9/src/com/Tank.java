package com;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * @author Chan
 * @version 1.0
 * @date 2019/10/21 17:17
 */
public class Tank {
    public static final int XSPEED = 5;
    public static final int YSPEED = 5;
    public static final int TANK_WIDTH = 30;
    public static final int TANK_HEIGHT = 30;

    private int x, y;

    private static Random r = new Random();

    private TankClient tc = null;

    private boolean good;
    private boolean live = true;

    private boolean bL = false, bU = false, bR = false, bD = false;

    public enum Direction {L, LU, U, RU, R, RD, D, LD, STOP;}
    private Direction dir = Direction.STOP;
    private Direction barrelDir = Direction.D;

    private int step = r.nextInt(12) + 3;

    public Tank(int x, int y, boolean good) {
        this.x = x;
        this.y = y;
        this.good = good;
    }

    public Tank(int x, int y, boolean good, Direction dir, TankClient tc) {
        this(x, y, good);
        this.dir = dir;
        this.tc = tc;
    }

    public void move() {
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
            case STOP:
        }

        if(this.dir != Direction.STOP) {
            this.barrelDir = this.dir;
        }

        if(x < 0) x = 0;
        if(y < 25) y = 25;
        if(x + Tank.TANK_WIDTH > TankClient.GAME_WIDTH) x = TankClient.GAME_WIDTH - Tank.TANK_WIDTH;
        if(y + Tank.TANK_HEIGHT > TankClient.GAME_HEIGHT) y = TankClient.GAME_HEIGHT - Tank.TANK_HEIGHT;

        if(!good) {
            Direction[] dirs = Direction.values();
            if(step == 0) {
                step = r.nextInt(12) + 3;
                int rn = r.nextInt(dirs.length);
                dir = dirs[rn];
            }
            step --;

            if(r.nextInt(40) > 38) {
                this.fire();
            }
        }
    }

    public void draw(Graphics g) {
        if(!this.isLive()) {
            if(!good) {
                tc.enemyTanks.remove(this);
            }
            return;
        }
        Color c = g.getColor();
        if(good){
            g.setColor(Color.RED);
        } else {
            g.setColor(Color.BLUE);
        }
        g.fillOval(x,y,TANK_WIDTH,TANK_HEIGHT);
        g.setColor(c);
        move();

        switch (barrelDir) {
            case L:
                g.drawLine(x + TANK_WIDTH/2, y + TANK_HEIGHT/2, x, y + TANK_HEIGHT/2);
                break;
            case LU:
                g.drawLine(x + TANK_WIDTH/2, y + TANK_HEIGHT/2, x, y);
                break;
            case U:
                g.drawLine(x + TANK_WIDTH/2, y + TANK_HEIGHT/2, x + TANK_WIDTH/2, y);
                break;
            case RU:
                g.drawLine(x + TANK_WIDTH/2, y + TANK_HEIGHT/2, x + TANK_WIDTH, y);
                break;
            case R:
                g.drawLine(x + TANK_WIDTH/2, y + TANK_HEIGHT/2, x + TANK_WIDTH, y + TANK_HEIGHT/2);
                break;
            case RD:
                g.drawLine(x + TANK_WIDTH/2, y + TANK_HEIGHT/2, x + TANK_WIDTH, y + TANK_HEIGHT);
                break;
            case D:
                g.drawLine(x + TANK_WIDTH/2, y + TANK_HEIGHT/2, x + TANK_WIDTH/2, y + TANK_HEIGHT);
                break;
            case LD:
                g.drawLine(x + TANK_WIDTH/2, y + TANK_HEIGHT/2, x, y + TANK_HEIGHT);
                break;
        }
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_LEFT:
                bL = true;
                break;
            case KeyEvent.VK_RIGHT:
                bR = true;
                break;
            case KeyEvent.VK_UP:
                bU = true;
                break;
            case KeyEvent.VK_DOWN:
                bD = true;
                break;
        }
        locateDirection();
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_CONTROL:
                fire();
                break;
            case KeyEvent.VK_LEFT:
                bL = false;
                break;
            case KeyEvent.VK_RIGHT:
                bR = false;
                break;
            case KeyEvent.VK_UP:
                bU = false;
                break;
            case KeyEvent.VK_DOWN:
                bD = false;
                break;
        }
        locateDirection();
    }

    public void locateDirection() {
        if (bL && !bU && !bR && !bD) dir = Direction.L;
        else if (bL && bU && !bR && !bD) dir = Direction.LU;
        else if (!bL && bU && !bR && !bD) dir = Direction.U;
        else if (!bL && bU && bR && !bD) dir = Direction.RU;
        else if (!bL && !bU && bR && !bD) dir = Direction.R;
        else if (!bL && !bU && bR && bD) dir = Direction.RD;
        else if (!bL && !bU && !bR && bD) dir = Direction.D;
        else if (bL && !bU && !bR && bD) dir = Direction.LD;
        else if (!bL && !bU && !bR && !bD) dir = Direction.STOP;
    }

    public Missile fire() {
        if(!live) return null;
        int x = this.x + TANK_WIDTH/2 - Missile.MISSILE_WIDTH/2;
        int y = this.y + TANK_HEIGHT/2 - Missile.MISSILE_HEIGHT/2;
        Missile m = new Missile(x, y, this.good, barrelDir, tc);
        tc.missiles.add(m);
        return m;
    }

    public Rectangle getRect() {
        return new Rectangle(x, y, TANK_WIDTH, TANK_HEIGHT);
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public boolean isGood() {
        return good;
    }
}
