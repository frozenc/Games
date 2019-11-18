package com;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
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
    public static final int UDP_PORT = 6666;
    List<Client> clients = new ArrayList<Client>();

    public void start() {
        new Thread(new UDPThread()).start();

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

    private class UDPThread implements Runnable {
        byte[] buff = new byte[100];

        @Override
        public void run() {
            DatagramSocket ds = null;
            try {
                ds = new DatagramSocket(UDP_PORT);
            } catch (SocketException e) {
                e.printStackTrace();
            }
System.out.println("UDPThread started at port : " + UDP_PORT);
            while(ds != null) {
                DatagramPacket dp = new DatagramPacket(buff, buff.length);
                try {
                    ds.receive(dp);
                    for (int i = 0; i < clients.size(); i++) {
                        Client c = clients.get(i);
                        String IP = c.IP;
                        int udpPort = c.udpPort;
                        dp.setSocketAddress(new InetSocketAddress(IP, udpPort));
                        ds.send(dp);
                    }
System.out.println("A packet received!");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
