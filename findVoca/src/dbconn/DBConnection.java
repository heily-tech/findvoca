package dbconn;

import java.sql.*;
import java.util.ArrayList;

public class DBConnection {
    class Learner {
        String id;
        String pw;

        public Learner(String id, String pw) {
            this.id = id;
            this.pw = pw;
        }
    }

    private static Connection conn;
    private static Statement stmt = null;
    private static final String user = "root";
    private static final String password = "1234";
    private static final String database = "";

    static PreparedStatement preStmt;

    public static ArrayList<Learner> lList = new ArrayList<Learner>();

    public DBConnection() { lList.add(new Learner("", ""));
    }

    public static Connection getConnection() {
        if (conn == null) {
            try {
                conn = DriverManager.getConnection(database, user, password);
                stmt = conn.createStatement(
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_UPDATABLE
                );
                String query = "create table if not exists learner(" +
                        "id varchar(30) primary key, " +
                        "nickname varchar(30), " +
                        "password varchar(30)" +
                        ")";
                stmt.executeUpdate(query);
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error Occurred.");
                System.exit(1);
            }
        } return conn;
    }

    public boolean signup(String id, String password) {
        for (Learner l : lList) {
            if (l.id.equals(id) && l.pw.equals(password)) {
                System.out.println("You are already exist.");
                return false;
            }
        }

        String query = "insert into learner(id, password) values(?, ?)";
        try {
            preStmt = DBConnection.getConnection().prepareStatement(query);
            preStmt.setString(1, id);
            preStmt.setString(2, password);
            preStmt.executeUpdate();

            System.out.println("Insert Success! ID : " + id + "PW : " + password);
            lList.add(new Learner(id, password));

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean login(String id, String password) {
        String query = "select id from learner where id= ? and password= ?";
        ResultSet resultSet = null;
        try {
            preStmt = DBConnection.getConnection().prepareStatement(query);
            preStmt.setString(1, id);
            preStmt.setString(2, password);
            resultSet = preStmt.executeQuery();

            if (resultSet.next()) {
                if (id.equals("admin") && password.equals("admin"))
                    System.out.println("Admin Login");
                else
                    for(Learner l : lList)
                        if (l.id.equals(id) && l.pw.equals(password))
                            System.out.println("User Login");
                return true;
            } return false;
        } catch (Exception e) {
            return false;
        }
    }
}
