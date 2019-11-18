package com;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Chan
 * @version 1.0
 * @date 2019/10/23 21:16
 */
public class TankServer {
    public static final int TCP_PORT = 8888;
    List<Client> clients = new ArrayList<Client>();

    public void start() {
        try {
            ServerSocket ss = new ServerSocket(TCP_PORT);
            while (true) {
                Socket s = ss.accept();
                DataInputStream dis = new DataInputStream(s.getInputStream());
                String IP = s.getInetAddress().getHostAddress();
                int udp = dis.readInt();
                s.close();
                Client c = new Client(IP, udp);
                clients.add(c);
System.out.println("A Client Connect! Addr:" + s.getInetAddress() + " " + s.getPort());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        TankServer ts = new TankServer();
        ts.start();
    }

    public class Client {
        String IP;
        int udpPort;

        public Client(String IP, int udpPort) {
            this.IP = IP;
            this.udpPort = udpPort;
        }
    }
}
