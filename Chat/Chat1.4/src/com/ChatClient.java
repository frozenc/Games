package com;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.*;

/**
 * @author Chan
 * @version 1.0
 * @date 2019/10/18 9:14
 */
public class ChatClient extends Frame {

    TextField tfTxt = new TextField();
    TextArea taContent = new TextArea();
    private Socket s = null;
    DataOutputStream dos = null;
    DataInputStream dis = null;
    private boolean bConnected = false;

    Thread tRecv = new Thread(new ResvThread());

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
                disconnect();
                System.exit(0);
            }
        });
        tfTxt.addActionListener(new TFListener());
        setVisible(true);
        connect();

        tRecv.start();
    }

    public void connect() {
        try {
            s = new Socket("127.0.0.1", 8888);
            dos = new DataOutputStream(s.getOutputStream());
            dis = new DataInputStream(s.getInputStream());
System.out.println("connected!");
            bConnected = true;

        } catch (ConnectException e) {
            System.out.println("Server nor found!");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void disconnect() {
                    try {
                dos.close();
                dis.close();
                s.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

//        try {
//            bConnected  = false;
//            tRecv.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                dos.close();
//                dis.close();
//                s.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }

    private class TFListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String str = tfTxt.getText().trim();
//            taContent.setText(str);

            try {
                dos.writeUTF(str);
                dos.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
//            System.out.println(str);
            tfTxt.setText("");
        }
    }

    class ResvThread implements Runnable {

        @Override
        public void run() {
            String str = null;
            try {
                while (bConnected) {
                    str = dis.readUTF();
                    System.out.println(str);
                    taContent.setText(taContent.getText() + str  + "\n");
                }
            } catch (SocketException e) {
                System.out.println("Connection close!");
//                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}