package com;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

/**
 * @author Chan
 * @version 1.0
 * @date 2019/10/25 9:38
 */
public class TankDeadMsg implements Msg {
    int msgType = Msg.TANK_DEAD_MSG;
    int id;
    TankClient tc;

    public TankDeadMsg(int id) {
        this.id = id;
System.out.println("A Tank Dead!id:" + id);
    }

    public TankDeadMsg(TankClient tc) {
        this.tc = tc;
    }

    @Override
    public void send(DatagramSocket ds, String IP, int udpPort) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        try {
            dos.writeInt(msgType);
            dos.writeInt(id);
System.out.println("sendMsgType:" + msgType + "-id:" + id);
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte[] buff = baos.toByteArray();
        DatagramPacket dp = new DatagramPacket(buff, buff.length, new InetSocketAddress(IP, udpPort));
        try {
            ds.send(dp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void parse(DataInputStream dis) {
        try {
            int id = dis.readInt();
            if(tc.myTank.id == id) {
                return;
            }
//            System.out.println("id:" + id + "-x:" + x + "-y:" + y + "-dir:" + dir + "-good:" + good);
            for (int i = 0; i < tc.enemyTanks.size(); i++) {
                Tank t = tc.enemyTanks.get(i);
                if(t.id == id) {
System.out.println("a tank dead");
                    t.setLive(false);
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
