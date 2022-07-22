package group_02.server.db;
import java.sql.*;

public class DB {
    public static Connection connectDB(String DB_URL ) {
        Driver myDriver = new com.microsoft.sqlserver.jdbc.SQLServerDriver();
        try {
            DriverManager.registerDriver(myDriver);
        } catch (SQLException e) {
        }

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
        }
        if (conn != null) {
            System.out.println("Connected");
        }

        return conn;

    }
    public static void main(String args[])  {
        String url = "jdbc:sqlserver://localhost:1433;databaseName=COVIDMANAGEMENT;user=sa;password=1;trustServerCertificate=true";
        Connection conn = connectDB(url);

    }
}
