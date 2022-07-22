package group_02.server.socket;

import java.io.*;
import java.net.Socket;

public class WorkerThread extends Thread {
    private Socket socket;

    public static DataInputStream dis = null;
    public static DataOutputStream dos = null;

    public WorkerThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        System.out.println("Processing: " + socket);
        try {
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());

            while(!socket.isClosed()){
                String flag = dis.readUTF();

                switch (flag) {
                    case "signIn":
                        String username = dis.readUTF();
                        String pass = dis.readUTF();
                        if(username.equals("phantuongvy") && pass.equals("123456")) {
                            dos.writeUTF("thanh cong");
                        } else {
                            dos.writeUTF("that bai");
                        }
                        break;
                    case "signUp":
                        String user = dis.readUTF();
                        String pwd = dis.readUTF();
                        if(user.equals("phantuongvy") && pwd.equals("123456")) {
                            dos.writeUTF("thanh cong");
                        } else {
                            dos.writeUTF("that bai");
                        }
                        break;
                }
            }
        } catch (IOException e) {
            System.err.println("Request Processing Error: " + e);
        }
        System.out.println("Complete processing: " + socket);
    }
}