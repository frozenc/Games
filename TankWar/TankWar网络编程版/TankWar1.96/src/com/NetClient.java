package com;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;

/**
 * @author Chan
 * @version 1.0
 * @date 2019/10/23 21:36
 */
public class NetClient {
    TankClient tc;
    private static int UDP_PORT_START = 2227;
    private int udpPort;

    DatagramSocket ds = null;

    public NetClient(TankClient tc) {
        this.tc = tc;
        udpPort = UDP_PORT_START ++;
        try {
            ds = new DatagramSocket(udpPort);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        new Thread(new UDPRecvThread()).start();
    }

    public void connect(String IP, int port) {
        Socket s = null;
        try {
            s = new Socket(IP, port);
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            dos.writeInt(udpPort);
            DataInputStream dis = new DataInputStream(s.getInputStream());
            int id = dis.readInt();
            tc.myTank.id = id;
System.out.println("Connected to Server! and Server give me a ID：" + id);
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

        TankNewMsg msg = new TankNewMsg(tc.myTank);
        send(msg);
    }

    public void send(Msg msg) {
        msg.send(ds, "127.0.0.1", TankServer.UDP_PORT);
    }

    private class UDPRecvThread implements Runnable {
        byte[] buff = new byte[100];

        @Override
        public void run() {
            while (true) {
                while(ds != null) {
                    DatagramPacket dp = new DatagramPacket(buff, buff.length);
                    try {
                        ds.receive(dp);
                        parse(dp);
System.out.println("A packet received from server!");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        private void parse(DatagramPacket dp) {
            ByteArrayInputStream bais = new ByteArrayInputStream(buff, 0, dp.getLength());
            DataInputStream dis = new DataInputStream(bais);
            int msgType = 0;
            try {
                msgType = dis.readInt();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Msg msg = null;
            switch (msgType) {
                case Msg.TANK_NEW_MSG:
System.out.println("A NEW MSG！");
                    msg = new TankNewMsg(NetClient.this.tc);
                    msg.parse(dis);
                    break;
                case Msg.TANK_MOVE_MSG:
                    msg = new TankMoveMsg(NetClient.this.tc);
System.out.println("A MOVE MSG！");
                    msg.parse(dis);
                    break;
            }
        }
    }
}
