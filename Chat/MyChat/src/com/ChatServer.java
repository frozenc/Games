package com;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.*;

/**
 * @author Chan
 * @version 1.0
 * @date 2019/10/19 20:03
 */
public class ChatServer {
    ServerSocket ss = null;
    List<Client> clients = new ArrayList<Client>();

    public static void main(String[] args) {
        new ChatServer().start();
    }

    public void start() {
        try {
            ss = new ServerSocket(8888);
        } catch (BindException e) {
            System.out.println("端口已连接...");
            System.out.println("请结束相关进程并重启服务器！");
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            while (true) {
                Socket s = ss.accept();
                System.out.println("A Client connected!");
                Client c = new Client(s);
                clients.add(c);
                new Thread(c).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally { //总是忘记关闭服务器
            try {
                ss.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class Client implements Runnable {

        Socket s = null;
        DataInputStream dis = null;
        DataOutputStream dos = null;

        Client(Socket s) {
            this.s = s;
            try {
                dis = new DataInputStream(s.getInputStream());
                dos = new DataOutputStream(s.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private boolean send(String str) {
            try {
                dos.writeUTF(str);
                return true;
            } catch (SocketException e) {
                System.out.println("A client Quit!");
                clients.remove(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    String str = dis.readUTF();
                    System.out.println(str);
                    for (int i = 0; i < clients.size(); i++) {
                        Client c = clients.get(i);
                        if(!c.send(str)) {
                            i --;
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("Client closed");
//                e.printStackTrace();
            } finally { //总是忘记释放资源
                try {
                    if(dis != null) {
                        dis.close();
                    }
                    if(dos != null) {
                        dos.close();
                    }
                    if(s != null) {
                        s.close();
                        s = null;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

    }

}
