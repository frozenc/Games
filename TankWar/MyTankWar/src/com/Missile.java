package com;

import com.sun.xml.internal.ws.policy.EffectiveAlternativeSelector;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Chan
 * @version 1.0
 * @date 2019/10/28 21:34
 */
public class Missile {
    public static final int SIZE = 10;
    private static final int X_SPEED = 10;
    private static final int Y_SPEED = 10;

    private int x;
    private int y;
    public Direction dir;
    private boolean live = true;

    private Tank t;
    private TankClient tc;

    private boolean good;

    private static Toolkit tk = Toolkit.getDefaultToolkit();

    private static Image[] missileImages = null;
    private static Map<String, Image> imgs = new HashMap<String, Image>();

    static {
        missileImages = new Image[] {
                tk.getImage(Missile.class.getClassLoader().getResource("images//missileL.gif")),
                tk.getImage(Missile.class.getClassLoader().getResource("images//missileLU.gif")),
                tk.getImage(Missile.class.getClassLoader().getResource("images//missileU.gif")),
                tk.getImage(Missile.class.getClassLoader().getResource("images//missileRU.gif")),
                tk.getImage(Missile.class.getClassLoader().getResource("images//missileR.gif")),
                tk.getImage(Missile.class.getClassLoader().getResource("images//missileRD.gif")),
                tk.getImage(Missile.class.getClassLoader().getResource("images//missileD.gif")),
                tk.getImage(Missile.class.getClassLoader().getResource("images//missileLD.gif")),
        };

        imgs.put("L", missileImages[0]);
        imgs.put("LU", missileImages[1]);
        imgs.put("U", missileImages[2]);
        imgs.put("RU", missileImages[3]);
        imgs.put("R", missileImages[4]);
        imgs.put("RD", missileImages[5]);
        imgs.put("D", missileImages[6]);
        imgs.put("LD", missileImages[7]);
    }

    Missile(int x, int y, boolean good,Direction dir, Tank t) {
        this.x = x;
        this.y = y;
        this.good = good;
        this.t = t;
        this.dir = dir;
    }

    Missile(int x, int y, boolean good, Direction dir, Tank t, TankClient tc) {
        this(x, y, good, dir, t);
        this.tc = tc;
    }

    public void draw(Graphics g) {
        if(!this.isLive()) {
            tc.missiles.remove(this);
        }

//        Color c = g.getColor();
//        if(good) {
//            g.setColor(Color.BLUE);
//        } else {
//            g.setColor(c);
//        }
//        g.fillOval(x, y, SIZE, SIZE);
//        g.setColor(c);

        switch (dir) {
            case L:
                g.drawImage(imgs.get("L"), x, y, null);
                break;
            case LU:
                g.drawImage(imgs.get("LU"), x, y, null);
                break;
            case U:
                g.drawImage(imgs.get("U"), x, y, null);
                break;
            case RU:
                g.drawImage(imgs.get("RU"), x, y, null);
                break;
            case R:
                g.drawImage(imgs.get("R"), x, y, null);
                break;
            case RD:
                g.drawImage(imgs.get("RD"), x, y, null);
                break;
            case D:
                g.drawImage(imgs.get("D"), x, y, null);
                break;
            case LD:
                g.drawImage(imgs.get("LD"), x, y, null);
                break;
        }

        move();
    }

    public void move() {
        if (x < 0 || y < 0 || x > TankClient.GAME_WIDTH || y > TankClient.GAME_HEIGHT) {
            this.setLive(false);
            return;
        }

        if (dir == Direction.L) {
            x -= X_SPEED;
        } else if (dir == Direction.LU) {
            x -= X_SPEED;
            y -= Y_SPEED;
        } else if (dir == Direction.U) {
            y -= Y_SPEED;
        } else if (dir == Direction.RU) {
            x += X_SPEED;
            y -= Y_SPEED;
        } else if (dir == Direction.R) {
            x += X_SPEED;
        } else if (dir == Direction.RD) {
            x += X_SPEED;
            y += Y_SPEED;
        } else if (dir == Direction.D) {
            y += Y_SPEED;
        } else if (dir == Direction.LD) {
            x -= X_SPEED;
            y += Y_SPEED;
        }
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public Rectangle getRect() {
        return new Rectangle(x, y, SIZE, SIZE);
    }

    public boolean hitTank(Tank t) {
        if (this.isLive() && (this.good != t.isGood()) && this.getRect().intersects(t.getRect()) && t.isLive()) {
            if(t.isGood()) {
                t.setLife(t.getLife() - 20);
                if(t.getLife() <= 0) {
                    t.setLive(false);
                }
            } else {
                t.setLive(false);
            }
            this.setLive(false);
            tc.explodes.add(new Explode(x, y, tc));
            return true;
        }

        return false;
    }

    public void hitTanks() {
        for (int i = 0; i < tc.tanks.size(); i++) {
            Tank t = tc.tanks.get(i);
            this.hitTank(t);
        }
    }

    public boolean hitWall(Wall w) {
        if (this.isLive() && this.getRect().intersects(w.getRect())) {
            tc.explodes.add(new Explode(x, y, tc));
            this.setLive(false);
            return true;
        }
        return false;
    }

    public void hitWalls() {
        for (int i = 0; i < tc.walls.size(); i++) {
            Wall w = tc.walls.get(i);
            this.hitWall(w);
        }
    }
}
