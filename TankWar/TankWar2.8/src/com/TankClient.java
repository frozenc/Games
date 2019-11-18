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
 * @date 2019/10/20 10:12
 */
public class TankClient extends Frame {

    public static final int GAME_WIDTH = 800;
    public static final int GAME_HEIGHT = 800;

    private Tank myTank = new Tank(50, 50, true, Direction.STOP, this);
    private Blood b = new Blood();

    List<Wall> walls = new ArrayList<Wall>();
    List<Tank> enemyTanks = new ArrayList<Tank>();
    List<Explode> explodes = new ArrayList<Explode>();
    List<Missile> missiles = new ArrayList<Missile>();
    private Image offScreenImage = null;

    private void launchFrame() {
        for (int i = 0; i < 10; i++) {
            enemyTanks.add(new Tank(50 + 40*(i + 1), 500, false, Direction.D, this));
        }

        walls.add(new Wall(200, 100, 200, 50, this));
        walls.add(new Wall(600, 500, 30, 100, this));

        this.setLocation(200, 100);
        this.setSize(GAME_WIDTH, GAME_HEIGHT);
        this.setTitle("TankWar");
        this.setResizable(false);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        this.setBackground(Color.BLACK);
        this.setVisible(true);

        this.addKeyListener(new KeyMonitor());

        new Thread(new PaintThread()).start();
    }

    public void paint(Graphics g) {
        g.drawString("missiles count" + missiles.size(), 10, 50);
        g.drawString("explodes count" + explodes.size(), 10, 70);
        g.drawString("enemy tanks count" + enemyTanks.size(), 10, 90);
        g.drawString("tanks life" + myTank.getLife(), 10, 110);

        if (enemyTanks.size() <= 0) {
            for (int i = 0; i < 5; i++) {
                enemyTanks.add(new Tank(50 + 40*(i + 1), 500, false, Direction.D, this));
            }
        }

        for (int i = 0; i < missiles.size(); i++) {
            Missile m = missiles.get(i);
            m.hitTanks(enemyTanks);
            m.hitTank(myTank);
            m.hitWalls(walls);
            m.draw(g);
        }

        for (int i = 0; i < explodes.size(); i++) {
            Explode e = explodes.get(i);
            e.draw(g);
        }

        for (int i = 0; i < enemyTanks.size(); i++) {
            Tank t = enemyTanks.get(i);
            t.collidesWithWalls(walls);
            t.collidesWithTanks(enemyTanks);
            t.draw(g);
        }

        for (int i = 0; i < walls.size(); i++) {
            Wall w = walls.get(i);
            w.draw(g);
        }
        b.draw(g);
        myTank.draw(g);
        myTank.eat(b);
    }

//    双缓冲解决闪烁现象
    @Override
    public void update(Graphics g) {
        if(offScreenImage == null){
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

    public static void main(String[] args) {
        new TankClient().launchFrame();
    }

    private class PaintThread implements Runnable {

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

    private class KeyMonitor extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            myTank.keyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            myTank.keyReleased(e);
        }
    }
}
