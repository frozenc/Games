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
    public static void main(String[] args) {
        new TankClient().launchFrame();
    }

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

        this.setVisible(true);
    }

}
