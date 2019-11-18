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
 * @date 2019/10/24 21:28
 */
public class MissileNewMsg implements Msg {
    int msgType = Msg.MISSILE_NEW_MSG;
    TankClient tc;
    Missile m;

//    两个构造方法一个用来接收一个用来发送
//    发送到server
    public MissileNewMsg(Missile m) {
        this.m = m;
    }

//    从server接收
    public MissileNewMsg(TankClient tc) {
        this.tc = tc;
    }

    @Override
    public void send(DatagramSocket ds, String IP, int udpPort) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        try {
            dos.writeInt(msgType);
            dos.writeInt(m.tankID);
            dos.writeInt(m.getX());
            dos.writeInt(m.getY());
            dos.writeInt(m.dir.ordinal());
            dos.writeBoolean(m.isGood());
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
            int tankID = dis.readInt();
            if(tankID == tc.myTank.id) {
                System.out.println("quit");
                return;
            }
            int x = dis.readInt();
            int y = dis.readInt();
            Dir dir = Dir.values()[dis.readInt()];
            boolean good = dis.readBoolean();

//System.out.println("id:" + id + "-x:" + x + "-y:" + y + "-dir:" + dir + "-good:" + good);
            Missile m = new Missile(tankID, x, y, good, dir, tc);
            tc.missiles.add(m);
            System.out.println("a missiles added！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
