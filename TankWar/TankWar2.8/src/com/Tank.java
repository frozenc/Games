package com;

import com.sun.xml.internal.ws.policy.EffectiveAlternativeSelector;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

/**
 * @author Chan
 * @version 1.0
 * @date 2019/10/21 17:17
 */
public class Tank {
    public static final int XSPEED = 5;
    public static final int YSPEED = 5;

    private int x, y;
    private int oldX, oldY;

    private static Random r = new Random();

    private TankClient tc = null;

    private boolean good;
    private boolean live = true;

    private int life = 100;
    private BloodBar bb = new BloodBar();

    private boolean bL = false, bU = false, bR = false, bD = false;

    private Direction dir = Direction.STOP;
    private Direction barrelDir = Direction.D;

    private int step = r.nextInt(12) + 3;

    private static Toolkit tk = Toolkit.getDefaultToolkit();

    private static Image[] tankImages = null;
    private static Map<String, Image> imgs = new HashMap<String, Image>();

    static {
        tankImages = new Image[] {
                tk.getImage(Tank.class.getClassLoader().getResource("images//tankL.gif")),
                tk.getImage(Tank.class.getClassLoader().getResource("images//tankLU.gif")),
                tk.getImage(Tank.class.getClassLoader().getResource("images//tankU.gif")),
                tk.getImage(Tank.class.getClassLoader().getResource("images//tankRU.gif")),
                tk.getImage(Tank.class.getClassLoader().getResource("images//tankR.gif")),
                tk.getImage(Tank.class.getClassLoader().getResource("images//tankRD.gif")),
                tk.getImage(Tank.class.getClassLoader().getResource("images//tankD.gif")),
                tk.getImage(Tank.class.getClassLoader().getResource("images//tankLD.gif")),
        };

        imgs.put("L", tankImages[0]);
        imgs.put("LU", tankImages[1]);
        imgs.put("U", tankImages[2]);
        imgs.put("RU", tankImages[3]);
        imgs.put("R", tankImages[4]);
        imgs.put("RD", tankImages[5]);
        imgs.put("D", tankImages[6]);
        imgs.put("LD", tankImages[7]);
    }

    public static final int TANK_WIDTH = 30;
    public static final int TANK_HEIGHT = 30;
//    public static final int TANK_HEIGHT = tankImages[0].getHeight();

    public Tank(int x, int y, boolean good) {
        this.x = x;
        this.y = y;
        this.oldX = x;
        this.oldY = y;
        this.good = good;
    }

    public Tank(int x, int y, boolean good, Direction dir, TankClient tc) {
        this(x, y, good);
        this.dir = dir;
        this.tc = tc;
    }

    public void move() {
//        记录上一步坦克的位置
        oldX = x;
        oldY = y;

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

//        判断是否撞墙
//        if(this.getRect().intersects())

        if(!good) {
            Direction[] dirs = Direction.values();
            if(step == 0) {
                step = r.nextInt(12) + 3;
                int rn = r.nextInt(dirs.length);
                dir = dirs[rn];
            }
            step --;

//            if(r.nextInt(40) > 38) {
//                this.fire();
//            }
        }
    }

    public void draw(Graphics g) {
        if(!this.isLive()) {
            if(!good) {
                tc.enemyTanks.remove(this);
            }
            return;
        }

        move();

        if(good) {
            bb.draw(g);
        }

//        根据炮筒方向画图片
        switch (barrelDir) {
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
            case KeyEvent.VK_F2:
                if(!this.isLive()) {
                    this.setLive(true);
                    this.setLife(100);
                }
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
            case KeyEvent.VK_A:
                superFire();
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

    public Missile fire(Direction dir) {
        if(!live) return null;
        int x = this.x + TANK_WIDTH/2 - Missile.MISSILE_WIDTH/2;
        int y = this.y + TANK_HEIGHT/2 - Missile.MISSILE_HEIGHT/2;
        Missile m = new Missile(x, y, this.good, dir, tc);
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

    public boolean collidesWithWall(Wall w) {
        if(this.live && this.getRect().intersects(w.getRect())) {
            this.stay();
            return true;
        }
        return false;
    }

    public boolean collidesWithWalls(List<Wall> walls) {
        for (int i = 0; i < walls.size(); i++) {
            Wall w = walls.get(i);
            if(collidesWithWall(w)){
                return true;
            }
        }
        return false;
    }

    private void stay() {
        this.x = oldX;
        this.y = oldY;
    }

    public boolean collidesWithTanks(List<Tank> tanks) {
        for (int i = 0; i < tanks.size(); i++) {
            Tank t = tanks.get(i);
            if (this != t) {
                if(this.live && t.isLive() && this.getRect().intersects(t.getRect())) {
                    this.stay();
                    t.stay();
                    return true;
                }
            }
        }
        return false;
    }

    private void superFire() {
        Direction[] dirs = Direction.values();
        for (int i = 0; i < 8; i++) {
            tc.missiles.add(fire(dirs[i]));
        }
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    private class BloodBar {
        public void draw(Graphics g) {
            Color c = g.getColor();
            g.setColor(Color.RED);
            g.drawRect(x+10, y-10, TANK_WIDTH, 10);
            int w = TANK_WIDTH * life / 100;
            g.fillRect(x+10, y-10, w, 10);
            g.setColor(c);
        }
    }

    public boolean eat(Blood b) {
        if(this.isLive() && b.isLive() && this.getRect().intersects(b.getRect())) {
            this.life = 100;
            b.setLive(false);
            return true;
        }
        return false;
    }
}
