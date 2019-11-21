package com;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * @author Chan
 * @version 1.0
 * @date 2019/10/18 10:41
 */
public class ChatServer {
    boolean started = false;
    ServerSocket ss = null;

    List<Client> clients = new ArrayList<Client>();

    public static void main(String[] args) {
        new ChatServer().start();
    }

    public void start() {
        try {
            ss = new ServerSocket(8888);
            started = true;
        } catch (BindException e) {
            System.out.println("端口使用中...");
            System.out.println("请关掉相关程序并重新运行服务器！");
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            while (started) {
                Socket s = ss.accept();
System.out.println("a client connected");
                Client c = new Client(s);
                clients.add(c);
                new Thread(c).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                ss.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class Client implements Runnable {
        private Socket s;
        private DataInputStream dis = null;
        private boolean bConnected = false;
        private DataOutputStream dos = null;

        public Client(Socket s) {
            this.s = s;
            try {
                dis = new DataInputStream(s.getInputStream());
                dos = new DataOutputStream(s.getOutputStream());
                bConnected = true;
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        public boolean send(String s) {
            try {
                dos.writeUTF(s);
                return true;
            } catch (SocketException e) {
                clients.remove(this);
                System.out.println("A client quit!");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        public void run() {
            try {
                while (bConnected) {
                    String str = dis.readUTF();
                    System.out.println(str);
                    for (int i = 0; i < clients.size(); i++) {
                        Client c = clients.get(i);
                        if(!c.send(str)) {
                            i --;
                        }
                    }
                }
//            } catch (SocketException e) {
//                clients.remove(this);
//                System.out.println("A client quit!");
            } catch (EOFException e) {
                System.out.println("Client closed!");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (dis != null) {
                        dis.close();
                    }
                    if (dos != null) {
                        dos.close();
                    }

                    if (s != null) {
                        s.close();
                        s = null;
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
