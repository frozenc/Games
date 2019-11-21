package com;

import java.awt.*;

/**
 * @author Chan
 * @version 1.0
 * @date 2019/10/18 9:14
 */
public class ChatClient extends Frame {
    public static void main(String[] args) {
        ChatClient cc = new ChatClient();
        cc.launchFrame();
    }

    public void launchFrame() {
        setLocation(400,300);
        setSize(300,300);
        setVisible(true);
    }
}
