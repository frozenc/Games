package com;

import java.awt.*;

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
        setLocation(200, 100);
        setSize(800, 800);
        setVisible(true);
    }

}
