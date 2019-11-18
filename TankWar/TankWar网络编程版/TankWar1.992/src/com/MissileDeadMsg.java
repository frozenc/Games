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
public class MissileDeadMsg implements Msg {
    int msgType = Msg.MISSILE_DEAD_MSG;
    int tankId;
    TankClient tc;
    int id;

    public MissileDeadMsg(int tankId, int id) {
        this.tankId = tankId;
        this.id = id;
System.out.println("A Missile Dead!id:" + id);
    }

    public MissileDeadMsg(TankClient tc) {
        this.tc = tc;
    }

    @Override
    public void send(DatagramSocket ds, String IP, int udpPort) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        try {
            dos.writeInt(msgType);
            dos.writeInt(tankId);
            dos.writeInt(id);
System.out.println("sendMsgType:" + msgType + "-TankId:" + tankId + "-id:" + id);
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
            int tankId = dis.readInt();
            int id = dis.readInt();
System.out.println("id:" + id + "-tankId:" + tankId);
            for (int i = 0; i < tc.missiles.size(); i++) {
                Missile m = tc.missiles.get(i);
                if(m.tankID == tankId && m.id == id) {
                    m.setLive(false);
                    tc.explodes.add(new Explode(m.getX(), m.getY(), tc));
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
