package group_02.server.socket;

import group_02.server.db.DB;
import group_02.server.models.Enote;
import org.apache.commons.io.*;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.ListIterator;

import static group_02.server.db.DB.*;

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

            while (!socket.isClosed()) {
                String flag = dis.readUTF();
                String username;
                String pass;
                String filename;
                File file;
                byte[] bytes;
                int noteId;
                Enote note;
                String absPath = "D:\\MMT\\file\\";


                switch (flag) {
                    case "signIn":
                        username = dis.readUTF();
                        pass = dis.readUTF();

                        dos.writeUTF(signIn(username,pass));

                        break;


                    case "signUp":
                        username = dis.readUTF();
                        pass = dis.readUTF();

                        dos.writeUTF(signUp(username,pass));

                        break;

                    case "getNote":
                        username = dis.readUTF();
                        noteId = dis.readInt();
                        file = new File(DB.getEnote(username, noteId).getFilePath());
                        bytes = new byte[(int) file.length()];
                        dos.write(bytes);
                        break;

                    case "getNoteList":
                        username = dis.readUTF();
                        ArrayList<Enote> list = getEnoteList(username);
                        ListIterator<Enote> iterate = list.listIterator();

                        while(iterate.hasNext()){
                            file = new File(DB.getEnote(username, iterate.next().getId()).getFilePath());
                            bytes = new byte[(int) file.length()];
                            dos.write(bytes);
                        }
                        break;
                    case "uploadNote":
                        username = dis.readUTF();
                        filename = dis.readUTF();
                        bytes = dis.readAllBytes();

                        FileUtils.writeByteArrayToFile(new File(absPath+filename), bytes);

                        saveEnote(new Enote(username,absPath+filename,filename.substring(filename.indexOf(".")+1).trim()));

                        break;

                }
            }
        } catch (IOException e) {
            System.err.println("Request Processing Error: " + e);
        }
        System.out.println("Complete processing: " + socket);
    }
}