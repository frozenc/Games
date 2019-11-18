package com;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author Chan
 * @version 1.0
 * @date 2019/10/20 10:12
 */
public class TankClient extends Frame {

    int x = 50, y = 50;

    private void launchFrame() {
        this.setLocation(200, 100);
        this.setSize(800, 800);
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

        new Thread(new PaintThread()).start();
    }

    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.RED);
        g.fillOval(x,y,50,50);
        g.setColor(c);

        y += 5;
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
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
