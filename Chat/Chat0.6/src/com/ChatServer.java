package com;

import java.io.IOException;
import java.net.*;
import java.sql.SQLOutput;

/**
 * @author Chan
 * @version 1.0
 * @date 2019/10/18 10:41
 */
public class ChatServer {

    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(8888);
            while (true) {
                Socket s = ss.accept();
System.out.println("a client connected");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
