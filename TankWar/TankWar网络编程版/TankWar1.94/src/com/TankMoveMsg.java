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
 * @date 2019/10/24 19:16
 */
public class TankMoveMsg implements Msg {
    int msgType = Msg.TANK_MOVE_MSG;

    int id;
    Dir dir;
    TankClient tc;

    public TankMoveMsg(int id, Dir dir) {
        this.id = id;
        this.dir = dir;
    }

    public TankMoveMsg(TankClient tc) {
        this(tc.myTank.id, tc.myTank.dir);
        this.tc = tc;
    }

    @Override
    public void send(DatagramSocket ds, String IP, int udpPort) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        try {
            dos.writeInt(msgType);
            dos.writeInt(id);
            dos.writeInt(dir.ordinal());
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
            Dir dir = Dir.values()[dis.readInt()];
//            System.out.println("id:" + id + "-x:" + x + "-y:" + y + "-dir:" + dir + "-good:" + good);
            boolean exist = false;
            for (int i = 0; i < tc.enemyTanks.size(); i++) {
                Tank t = tc.enemyTanks.get(i);
                if(t.id == id) {
                    t.dir = dir;
                    exist = true;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
