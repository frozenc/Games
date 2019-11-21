package com;

import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;

/**
 * @author Chan
 * @version 1.0
 * @date 2019/10/19 16:56
 */
public class ChatClient extends Frame{
    TextField tfTxt = new TextField();
    TextArea taContent = new TextArea();
    Socket s = null;
    DataInputStream dis = null;
    DataOutputStream dos = null;
    ResvThread resv = new ResvThread();

    public static void main(String[] args) {
        new ChatClient().launchFrame();
    }

    public void launchFrame() {
        setLocation(300,300);
        setSize(300,300);
        setVisible(true);

        add(tfTxt, BorderLayout.NORTH);
        add(taContent, BorderLayout.SOUTH);
        pack();

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
                disConnect();
            }
        });

        tfTxt.addActionListener(new tfListener());

        connect();
        new Thread(resv).start();
    }

    public void connect() {
//        没有独立成connect方法
        try {
            s = new Socket("127.0.0.1", 8888);
            System.out.println("Connected!");
            dos = new DataOutputStream(s.getOutputStream());
            dis = new DataInputStream(s.getInputStream());
        } catch (ConnectException e) {
//            忘记了服务器连接异常
            System.out.println("server not found!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void disConnect() {
        try {
            dos.close();
            dis.close();
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private class tfListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String str = tfTxt.getText().trim();
            tfTxt.setText("");
//            taContent.setText(str);
            try {
                dos.writeUTF(str);
                dos.flush();
//                System.out.println(str);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    class ResvThread implements Runnable {

        @Override
        public void run() {
            String str = null;
            try {
                while(true) {
                    str = dis.readUTF();
                    taContent.setText(taContent.getText() + str + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
