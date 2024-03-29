package com;

import java.io.DataInputStream;
import java.io.DataOutputStream;
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
    private static int ID = 100;
    public static final int TCP_PORT = 8888;
    List<Client> clients = new ArrayList<Client>();

    public void start() {
        ServerSocket ss = null;
        try {
            ss = new ServerSocket(TCP_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            Socket s = null;
            try {
                s = ss.accept();
                DataInputStream dis = new DataInputStream(s.getInputStream());
                String IP = s.getInetAddress().getHostAddress();
                int udp = dis.readInt();
                Client c = new Client(IP, udp);
                clients.add(c);
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                dos.writeInt(ID++);
                System.out.println("A Client Connect! Addr:" + s.getInetAddress() + " " + s.getPort() + "----udpPort:" + udp);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(s != null) {
                    try {
                        s.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
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
