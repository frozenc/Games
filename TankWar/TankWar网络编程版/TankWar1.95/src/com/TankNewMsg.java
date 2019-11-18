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
 * @date 2019/10/24 10:43
 */
public class TankNewMsg implements Msg {
    int msgType = Msg.TANK_NEW_MSG;

    Tank tank;
    TankClient tc;

    public TankNewMsg(TankClient tc) {
        this.tc = tc;
    }

    public TankNewMsg(Tank tank) {
        this.tank = tank;
    }

    public void send(DatagramSocket ds, String IP, int udpPort) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        try {
            dos.writeInt(msgType);
            dos.writeInt(tank.id);
            dos.writeInt(tank.x);
            dos.writeInt(tank.y);
            dos.writeInt(tank.dir.ordinal());
            dos.writeBoolean(tank.isGood());
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


    public void parse(DataInputStream dis) {
        try {
            int id = dis.readInt();
            if(tc.myTank.id == id) {
                return;
            }
            int x = dis.readInt();
            int y = dis.readInt();
            Dir dir = Dir.values()[dis.readInt()];
            boolean good = dis.readBoolean();
System.out.println("id:" + id + "-x:" + x + "-y:" + y + "-dir:" + dir + "-good:" + good);
            Tank t = new Tank(x, y, good, dir, tc);
            t.id = id;
            tc.enemyTanks.add(t);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
