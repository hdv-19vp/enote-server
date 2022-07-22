package group_02.server.models;

import java.io.File;

public class Enote {
    int id;
    String username;
    String filePath;
    String fileType;

    public byte[] getBuffer() {
        return buffer;
    }

    public void setBuffer(byte[] buffer) {
        this.buffer = buffer;
    }

    byte[] buffer = null;

    public Enote(int id, String username, String filePath, String fileType) {
        this.id = id;
        this.username = username;
        this.filePath = filePath;
        this.fileType = fileType;
    }

    public Enote(String username, String filePath, String fileType) {
        this.username = username;
        this.filePath = filePath;
        this.fileType = fileType;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getFileType() {
        return fileType;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    @Override
    public String toString() {
        return "Enote{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", filePath='" + filePath + '\'' +
                ", fileType='" + fileType + '\'' +
                '}';
    }
}
