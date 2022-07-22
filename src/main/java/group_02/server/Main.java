package group_02.server;

import java.sql.Connection;

import static group_02.server.db.DB.connectDB;

public class Main {
    public static void main(String[] args) {

        String url = "jdbc:sqlserver://localhost:1433;databaseName=Enote;user=sa;password=1;trustServerCertificate=true";
        Connection conn = connectDB(url);
    }
}
