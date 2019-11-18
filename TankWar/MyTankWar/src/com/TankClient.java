package com;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;
import java.util.List;

/**
 * @author Chan
 * @version 1.0
 * @date 2019/10/28 20:12
 */
public class TankClient extends Frame {
    public static final int GAME_WIDTH = 800;
    public static final int GAME_HEIGHT = 600;

    public Tank myTank;
    public Blood b;

    public List<Missile> missiles = new ArrayList<Missile>();
    public List<Tank> tanks = new ArrayList<Tank>();
    public List<Explode> explodes = new ArrayList<Explode>();
    public List<Wall> walls = new ArrayList<Wall>();

//    双缓冲后备图像
    private Image offScreenImage = null;

    public static void main(String[] args) {
        new TankClient().launchFrame();
    }

    public void paint(Graphics g) {
        g.drawString("missiles count:" + missiles.size(), 10, 50);

        myTank.draw(g);
        myTank.hitBlood();
        b.draw(g);

        for (int i = 0; i < walls.size(); i++) {
            Wall w = walls.get(i);
            w.draw(g);
        }

        for (int i = 0; i < missiles.size(); i++) {
            Missile m = missiles.get(i);
            m.hitTanks();
            m.hitTank(myTank);
            m.hitWalls();
            if (m.isLive()) {
                m.draw(g);
            } else {
                missiles.remove(m);
            }
        }

        for (int i = 0; i < tanks.size(); i++) {
            Tank t = tanks.get(i);
            if (t.isLive()) {
                t.draw(g);
            } else {
                tanks.remove(t);
            }
        }

        for (int i = 0; i < explodes.size(); i++) {
            Explode e = explodes.get(i);
            if (e.isLive()) {
                e.draw(g);
            } else {
                tanks.remove(e);
            }
        }

        if(tanks.size() == 0) {
            int reproduceTankCount = Integer.parseInt(PropertyMgr.getProperty("reproduceTankCount"));
            for (int i = 0; i < reproduceTankCount; i++) {
                Tank t = new Tank(50 + 50 * i, 400, false, Direction.D, this);
                tanks.add(t);
            }
        }
    }

//    运行，初始化窗口
    public void launchFrame() {
        int initTankCount = Integer.parseInt(PropertyMgr.getProperty("initTankCount"));

        walls.add(new Wall(600,300, 50, 200));
        walls.add(new Wall(100,300, 200, 50));

        b = new Blood();

        for (int i = 0; i < initTankCount; i++) {
            Tank t = new Tank(50 + 50 * i, 400, false, Direction.D, this);
            tanks.add(t);
        }

        setLocation(300, 300);
        setSize(GAME_WIDTH, GAME_HEIGHT);
        setBackground(Color.BLACK);
        setTitle("TankWar");
        setResizable(false);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        setVisible(true);

        myTank = new Tank(200, 200, true, Direction.STOP, this);
        new Thread(new paintThread()).start();

        this.addKeyListener(new keyMonitor());
    }

//    双缓冲解决屏幕闪烁
    @Override
    public void update(Graphics g) {
        if (offScreenImage == null) {
            offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
        }
        Graphics gOffScreen = offScreenImage.getGraphics();
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.BLACK);
        gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        gOffScreen.setColor(c);
        paint(gOffScreen);
        g.drawImage(offScreenImage, 0, 0, null);
    }

//    线程类，让物体运动起来
    class paintThread implements Runnable {
        @Override
        public void run() {
            while (true) {
                repaint();
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

//    键盘监听类
    class keyMonitor extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            myTank.keyPressed(e);
        }

        public void keyReleased(KeyEvent e) {
            myTank.keyReleased(e);
        }
    }
}
