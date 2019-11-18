package com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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

    public Tank myTank = new Tank(50, 50, true, Dir.STOP, this);

    List<Tank> enemyTanks = new ArrayList<Tank>();
    List<Explode> explodes = new ArrayList<Explode>();
    List<Missile> missiles = new ArrayList<Missile>();
    private Image offScreenImage = null;

    NetClient nc = new NetClient(this);

    ConnDialog dialog = new ConnDialog();

    private void launchFrame() {
//        for (int i = 0; i < 10; i++) {
//            enemyTanks.add(new Tank(50 + 40*(i + 1), 50, false, Dir.D, this));
//        }

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
        this.setBackground(Color.GREEN);
        this.setVisible(true);

        this.addKeyListener(new KeyMonitor());

        new Thread(new PaintThread()).start();

//        nc.connect("127.0.0.1", TankServer.TCP_PORT);
    }

    public void paint(Graphics g) {
        g.drawString("missiles count" + missiles.size(), 10, 50);
        g.drawString("explodes count" + explodes.size(), 10, 70);
        g.drawString("enemy tanks count" + enemyTanks.size(), 10, 90);
        for (int i = 0; i < missiles.size(); i++) {
            Missile m = missiles.get(i);
            m.hitTanks(enemyTanks);
            m.hitTank(myTank);
            m.draw(g);
        }

        for (int i = 0; i < explodes.size(); i++) {
            Explode e = explodes.get(i);
            e.draw(g);
        }

        for (int i = 0; i < enemyTanks.size(); i++) {
            Tank t = enemyTanks.get(i);
            t.draw(g);
        }

        myTank.draw(g);
    }

//    双缓冲解决闪烁现象
    @Override
    public void update(Graphics g) {
        if(offScreenImage == null){
            offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
        }
        Graphics gOffScreen = offScreenImage.getGraphics();
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.GREEN);
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
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_C) {
                dialog.setVisible(true);
            } else {
                myTank.keyPressed(e);
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            myTank.keyReleased(e);
        }
    }

    class ConnDialog extends Dialog {
        Button b = new Button("connect");
        TextField tfIp = new TextField("127.0.0.1", 12);
        TextField tfPort = new TextField("" + TankServer.TCP_PORT, 4);
        TextField tfMyUDPPort = new TextField("2223", 4);

        public ConnDialog() {
            super(TankClient.this, true);
            this.setLocation(300,300);
            this.setLayout(new FlowLayout());
            this.add(new Label("IP"));
            this.add(tfIp);
            this.add(new Label("Port:"));
            this.add(tfPort);
            this.add(new Label("My UDP Port:"));
            this.add(tfMyUDPPort);
            this.add(b);
            this.pack();

            this.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    setVisible(false);
                }
            });

            b.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String IP = tfIp.getText().trim();
                    int port = Integer.parseInt(tfPort.getText().trim());
                    int udpPort = Integer.parseInt(tfMyUDPPort.getText().trim());
                    nc.setUdpPort(udpPort);
                    nc.connect(IP, port);
                    setVisible(false);
                }
            });
        }
    }
}
