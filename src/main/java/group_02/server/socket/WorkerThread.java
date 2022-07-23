package group_02.server.socket;

import group_02.server.db.DB;
import group_02.server.models.Enote;
import org.apache.commons.io.*;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.sql.Connection;
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
                String absPath = "D:\\MMT\\file\\";

                String url = "jdbc:sqlserver://localhost:1433;databaseName=Enote;user=sa;password=1;trustServerCertificate=true";
                Connection conn = connectDB(url);
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
                        Enote note = DB.getEnote(username, noteId);
                        file = new File(note.getFilePath());
                        bytes = Files.readAllBytes(file.toPath());
                        dos.writeInt(bytes.length);
                        dos.write(bytes);
                        dos.writeUTF(note.getFilePath());

                        dos.writeUTF("success");
                        break;

                    case "getNoteList":
                        username = dis.readUTF();
                        ArrayList<Enote> list = getEnoteList(username);
                        ListIterator<Enote> iterate = list.listIterator();

                        dos.writeInt(list.size());

                        while(iterate.hasNext()){
                            Enote temp = iterate.next();
                            dos.writeUTF(temp.getUsername());
                            dos.writeInt(temp.getId());
                            dos.writeUTF(temp.getFilePath());
                            dos.writeUTF(temp.getFileType());
                        }


                        //dos.writeUTF("success");
                        break;
                    case "saveNote":
                        username = dis.readUTF();
                        filename = dis.readUTF();
                        bytes = null;
                        int length = dis.readInt();
                        if(length > 0) {
                            bytes = new byte[length];
                            dis.readFully(bytes);
                        }

                        FileUtils.writeByteArrayToFile(new File(absPath+filename), bytes);


                        saveEnote(new Enote(username,absPath+filename,filename.substring(filename.indexOf(".")+1).trim()));

                        dos.writeUTF("success");
                        break;

                }
            }
        } catch (IOException e) {
            System.err.println("Request Processing Error: " + e);
        }
        System.out.println("Complete processing: " + socket);
    }
}