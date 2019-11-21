package com;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.*;

/**
 * @author Chan
 * @version 1.0
 * @date 2019/10/18 9:14
 */
public class ChatClient extends Frame {

    TextField tfTxt = new TextField();
    TextArea taContent = new TextArea();

    public static void main(String[] args) {
        new ChatClient().launchFrame();
    }

    public void launchFrame() {
        setLocation(400, 300);
        setSize(300, 300);

        add(taContent, BorderLayout.NORTH);
        add(tfTxt, BorderLayout.SOUTH);
        pack();

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        tfTxt.addActionListener(new TFListener());
        setVisible(true);
        connect();
    }

    public void connect() {
        try {
            Socket s = new Socket("127.0.0.1", 8888);
System.out.println("connected!");
        } catch (ConnectException e) {
            System.out.println("Server nor found!");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private class TFListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String s = tfTxt.getText().trim();
            taContent.setText(s);
            tfTxt.setText("");
        }
    }

}