package com;

import java.io.DataInputStream;
import java.net.DatagramSocket;

/**
 * @author Chan
 * @version 1.0
 * @date 2019/10/24 19:22
 */
public interface Msg {
    public static final int TANK_NEW_MSG = 1;
    public static final int TANK_MOVE_MSG = 2;
    public static final int MISSILE_NEW_MSG = 3;

    public void send(DatagramSocket ds, String IP, int udpPort);
    public void parse(DataInputStream dis);
}
