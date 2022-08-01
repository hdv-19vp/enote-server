package group_02.server.db;

import group_02.server.models.Enote;

import java.sql.*;
import java.util.ArrayList;

public class DB {
    public static Connection conn = null;

    /**
     *
     * @param DB_URL
     * @return Connection
     */
    public static Connection connectDB(String DB_URL) {
        try {
            conn = DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
        }
        return conn;
    }

    /**
     * @param username
     * @param password
     * @return "success" || "wrong password" || "username does not exist" || "error"
     */
    public static String signIn(String username, String password) {
        String result = "error";
        try {
            CallableStatement cstmt = conn.prepareCall("{call dbo.usp_signin(?,?,?)}");
            cstmt.setString(1, username);
            cstmt.setString(2, password);
            cstmt.registerOutParameter(3, Types.VARCHAR);
            cstmt.execute();

            result = cstmt.getNString(3);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * @param username
     * @param password
     * @return "success" || "username already taken" || "error"
     */
    public static String signUp(String username, String password) {
        String result = "error";
        try {
            CallableStatement cstmt = conn.prepareCall("{call dbo.usp_signup(?,?,?)}");
            cstmt.setString(1, username);
            cstmt.setString(2, password);
            cstmt.registerOutParameter(3, Types.VARCHAR);
            cstmt.execute();

            result = cstmt.getNString(3);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * @param username
     * @param filePath
     * @param fileType
     * @return "success" || "error"
     */
    public static String saveEnote(Enote enote) {
        String result = "error";
        try {
            PreparedStatement ps = conn.prepareCall("insert into enote(username,files_path,files_type) values(?,?,?)");
            ps.setString(1, enote.getUsername());
            ps.setString(2, enote.getFilePath());
            ps.setString(3, enote.getFileType());
            ps.execute();

            result = "success";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     *
     * @param username
     * @return ArrayList<Enote>
     */
    public static ArrayList<Enote> getEnoteList(String username) {
        ArrayList<Enote> noteList = new ArrayList<>();
        try (CallableStatement cstmt = conn.prepareCall("select * from Enote where username = ?");) {
            cstmt.setString(1, username);

            boolean result = cstmt.execute();
            if (result) {
                ResultSet rs = cstmt.getResultSet();
                while (rs.next()) {
                    noteList.add(
                            new Enote(
                                    rs.getInt("id_note"),
                                    rs.getString("username"),
                                    rs.getString("files_path"),
                                    rs.getString("files_type")
                            )
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return noteList;
    }

    /**
     *
     * @param username
     * @param noteID
     * @return Enote
     */
    public static Enote getEnote(String username, Integer noteID) {
        Enote enote = null;
        try (PreparedStatement ps = conn.prepareCall("select * from Enote where username = ? and id_note = ?")) {
            ps.setString(1, username);
            ps.setInt(2, noteID);
            ps.execute();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                enote=new Enote( rs.getInt("id_note"),username,rs.getString("files_path"),rs.getString("files_type"));
            }

        }
        catch (Exception e){
            e.printStackTrace();

        }
        return enote;
    }


    public static void main(String args[]) {
        String url = "jdbc:sqlserver://localhost:1433;databaseName=Enote;user=sa;password=1;trustServerCertificate=true";
        Connection conn = connectDB(url);
        //System.out.println(signIn("hoan3232","123"));

        //System.out.println(saveEnote(new Enote("hoan3232", "aaaaa", "aaa")));
        System.out.println(DB.getEnote("hoan3232",1));
    }
}
