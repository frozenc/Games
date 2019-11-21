package com;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author Chan
 * @version 1.0
 * @date 2019/10/18 9:14
 */
public class ChatClient extends Frame {

    TextField tfTxt = new TextField();
    TextArea tfA = new TextArea();

    public static void main(String[] args) {
        ChatClient cc = new ChatClient();
        cc.launchFrame();
    }

    public void launchFrame() {
        setLocation(400, 300);
        setSize(300, 300);

        add(tfA, BorderLayout.NORTH);
        add(tfTxt, BorderLayout.SOUTH);
        pack();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        setVisible(true);
    }
}