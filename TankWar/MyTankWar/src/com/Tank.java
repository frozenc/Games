package com;

import java.awt.*;
import java.awt.event.KeyEvent;

import static com.Direction.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author Chan
 * @version 1.0
 * @date 2019/10/28 20:11
 */
public class Tank {
    public static final int X_SPEED = 5;
    public static final int Y_SPEED = 5;
    public static final int TANK_WIDTH = 30;
    public static final int TANK_HEIGHT = 30;

    private int x;
    private int y;
    private int oldX;
    private int oldY;
    private boolean good = true;
    private boolean live = true;
    private int life = 100;

    private TankClient tc;
    private boolean bR = false, bL = false, bU = false, bD = false;

    public Direction dir = STOP;

    public Direction barrelDir = D;

    private Random random = new Random();

    private int step = random.nextInt(12) + 3;

    private static Toolkit tk = Toolkit.getDefaultToolkit();

    private static Image[] missileImages = null;
    private static Map<String, Image> imgs = new HashMap<String, Image>();

    static {
        missileImages = new Image[] {
                tk.getImage(Tank.class.getClassLoader().getResource("images//tankL.gif")),
                tk.getImage(Tank.class.getClassLoader().getResource("images//tankLU.gif")),
                tk.getImage(Tank.class.getClassLoader().getResource("images//tankU.gif")),
                tk.getImage(Tank.class.getClassLoader().getResource("images//tankRU.gif")),
                tk.getImage(Tank.class.getClassLoader().getResource("images//tankR.gif")),
                tk.getImage(Tank.class.getClassLoader().getResource("images//tankRD.gif")),
                tk.getImage(Tank.class.getClassLoader().getResource("images//tankD.gif")),
                tk.getImage(Tank.class.getClassLoader().getResource("images//tankLD.gif")),
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

    Tank(int x, int y, boolean good, Direction dir) {
        this.x = x;
        this.y = y;
        this.good = good;
        this.dir = dir;
    }

    Tank(int x, int y, boolean good, Direction dir, TankClient tc) {
        this(x, y, good, dir);
        this.tc = tc;
    }

//    让坦克动起来
    public void move() {
        if(good) {
            locateDirection();
        }

        this.oldX = this.x;
        this.oldY = this.y;

        if (dir != STOP) {
            barrelDir = dir;
        }

        if (dir == L) {
            x -= X_SPEED;
        } else if (dir == LU) {
            x -= X_SPEED;
            y -= Y_SPEED;
        } else if (dir == U) {
            y -= Y_SPEED;
        } else if (dir == RU) {
            x += X_SPEED;
            y -= Y_SPEED;
        } else if (dir == R) {
            x += X_SPEED;
        } else if (dir == RD) {
            x += X_SPEED;
            y += Y_SPEED;
        } else if (dir == D) {
            y += Y_SPEED;
        } else if (dir == LD) {
            x -= X_SPEED;
            y += Y_SPEED;
        }

        this.collapseWithWall();
        this.collapseWithTanks();

        if (x < 0 || y < 25 || x > TankClient.GAME_WIDTH - TANK_WIDTH || y > TankClient.GAME_HEIGHT - TANK_HEIGHT) {
            this.x = oldX;
            this.y = oldY;
            this.dir = STOP;
        }

        if (!good) {
            if (step == 0) {
                int r = random.nextInt(8);
                this.dir = Direction.values()[r];
                step = random.nextInt(12) + 3;
            }
            step --;

            if (random.nextInt(40) > 35) {
                this.fire();
            }

        }

    }

//    将坦克画出来
    public void draw(Graphics g) {
        if (!this.isLive()) {
            if (!good) {
                tc.tanks.remove(this);
            }
            return;
        }

        Color c = g.getColor();
        if(this.good) {
            g.setColor(Color.RED);
            BloodBar bb = new BloodBar();
            bb.draw(g);
        } else {
            g.setColor(Color.BLUE);
        }
//        g.fillOval(x, y, TANK_WIDTH, TANK_HEIGHT);
        g.setColor(c);

//        switch (barrelDir) {
//            case L:
//                g.drawLine(x, y + TANK_HEIGHT/2, x + TANK_WIDTH/2, y + TANK_HEIGHT/2);
//                break;
//            case LU:
//                g.drawLine(x, y, x + TANK_WIDTH/2, y + TANK_HEIGHT/2);
//                break;
//            case U:
//                g.drawLine(x + TANK_WIDTH/2, y, x + TANK_WIDTH/2, y + TANK_HEIGHT/2);
//                break;
//            case RU:
//                g.drawLine(x + TANK_WIDTH, y, x + TANK_WIDTH/2, y + TANK_HEIGHT/2);
//                break;
//            case R:
//                g.drawLine(x + TANK_WIDTH, y + TANK_HEIGHT/2, x + TANK_WIDTH/2, y + TANK_HEIGHT/2);
//                break;
//            case RD:
//                g.drawLine(x + TANK_WIDTH, y + TANK_HEIGHT, x + TANK_WIDTH/2, y + TANK_HEIGHT/2);
//                break;
//            case D:
//                g.drawLine(x + TANK_WIDTH/2, y + TANK_HEIGHT, x + TANK_WIDTH/2, y + TANK_HEIGHT/2);
//                break;
//            case LD:
//                g.drawLine(x, y + TANK_HEIGHT, x + TANK_WIDTH/2, y + TANK_HEIGHT/2);
//                break;
//        }

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

        move();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isGood() {
        return good;
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    //    根据键盘事件，选择方向
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_UP:
                bU = true;
                break;
            case KeyEvent.VK_DOWN:
                bD = true;
                break;
            case KeyEvent.VK_LEFT:
                bL = true;
                break;
            case KeyEvent.VK_RIGHT:
                bR = true;
                break;
        }
    }

//    根据键盘事件，选择方向
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_F2:
                this.setLife(100);
                this.setLive(true);
            case KeyEvent.VK_A:
                this.superFire();
                break;
            case KeyEvent.VK_CONTROL:
                this.fire();
                break;
            case KeyEvent.VK_UP:
                bU = false;
                break;
            case KeyEvent.VK_DOWN:
                bD = false;
                break;
            case KeyEvent.VK_LEFT:
                bL = false;
                break;
            case KeyEvent.VK_RIGHT:
                bR = false;
                break;
        }
    }

//    根据键盘事件确定坦克方向
    private void locateDirection() {
        if (!bU && !bD && !bR && !bL) dir = STOP;
        else if (!bU && !bD && !bR && bL) dir = L;
        else if (bU && !bD && !bR && bL) dir = LU;
        else if (bU && !bD && !bR && !bL) dir = U;
        else if (bU && !bD && bR && !bL) dir = RU;
        else if (!bU && !bD && bR && !bL) dir = R;
        else if (!bU && bD && bR && !bL) dir = RD;
        else if (!bU && bD && !bR && !bL) dir = D;
        else if (!bU && bD && !bR && bL) dir = LD;
    }

    public void superFire() {
        for (int i = 0; i < 8; i++) {
            fire(Direction.values()[i]);
        }
    }

    public Missile fire(Direction dir) {
        if(!this.isLive()) return null;
        int x = this.getX() + TANK_WIDTH/2 - Missile.SIZE/2;
        int y = this.getY() + TANK_HEIGHT/2 - Missile.SIZE/2;
        Missile m = new Missile(x, y, good, dir,this, tc);
        tc.missiles.add(m);
        return m;
    }

    public Missile fire() {
        if(!this.isLive()) return null;
        int x = this.getX() + TANK_WIDTH/2 - Missile.SIZE/2;
        int y = this.getY() + TANK_HEIGHT/2 - Missile.SIZE/2;
        Missile m = new Missile(x, y, good, barrelDir,this, tc);
        tc.missiles.add(m);
        return m;
    }

    public Rectangle getRect() {
        return new Rectangle(x, y, TANK_WIDTH, TANK_HEIGHT);
    }

    public void collapseWithWall() {
        for (int i = 0; i < tc.walls.size(); i++) {
            Wall w = tc.walls.get(i);
            if(!this.good && this.getRect().intersects(w.getRect()) ) {
                this.stay();
            }
        }
    }

    public void stay() {
        this.x = this.oldX;
        this.y = this.oldY;
    }

    public void collapseWithTanks() {
        for (int i = 0; i < tc.tanks.size(); i++) {
            Tank t = tc.tanks.get(i);
            if (this != t) {
                if(this.getRect().intersects(t.getRect())) {
                    this.stay();
                    t.stay();
                }
            }
        }
        if(!good) {
            if (this.getRect().intersects(tc.myTank.getRect())) {
                this.stay();
            }
        }
    }

    private class BloodBar {
        public void draw(Graphics g) {
            Color c = g.getColor();
            g.setColor(Color.RED);
            g.drawRect(x, y-10, TANK_WIDTH, 10);
            int w = TANK_WIDTH * getLife() / 100;
            g.fillRect(x, y-10, w, 10);
            g.setColor(c);
        }
    }

    public void hitBlood() {
        if(this.isLive() && this.getRect().intersects(tc.b.getRect())) {
            tc.b.setLive(false);
            this.setLife(100);
        }
    }

}
