package com;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * @author Chan
 * @version 1.0
 * @date 2019/10/23 21:36
 */
public class NetClient {
    private static int UDP_PORT_START = 2223;
    private int udpPort;

    public NetClient() {
        udpPort = UDP_PORT_START ++;
    }

    public void connect(String IP, int port) {
        Socket s = null;
        try {
            s = new Socket(IP, port);
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            dos.writeInt(udpPort);
System.out.println("Connected to Server!");
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
