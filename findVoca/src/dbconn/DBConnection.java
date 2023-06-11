package dbconn;

import program.Learner;

import java.sql.*;
import java.util.ArrayList;

public class DBConnection {


    private static Connection conn;
    private static Statement stmt = null;
    private static final String user = "root";
    private static final String password = "1234";
    private static final String database = "jdbc:mysql://localhost:3306/findvoca";

    static PreparedStatement preStmt;

    public static ArrayList<Learner> lList = new ArrayList<Learner>();

    public DBConnection() {
        lList.add(new Learner("", "", ""));
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

    public boolean signup(String id, String password, String nickname) {
        if (id.isEmpty() || password.isEmpty() || nickname.isEmpty()) {
            System.out.println("입력되지 않은 내용이 있습니다.");
            return false;
        }

        for (Learner l : lList) {
            if (l.getId().equals(id) && l.getNickname().equals(nickname)) {
                System.out.println("이미 존재하는 사용자입니다.");
                return false;
            }
        }

        String query = "insert into learner(id, password, nickname) values(?, ?, ?)";
        try {
            preStmt = DBConnection.getConnection().prepareStatement(query);
            preStmt.setString(1, id);
            preStmt.setString(2, password);
            preStmt.setString(3, nickname);
            preStmt.executeUpdate();
            lList.add(new Learner(id, password, nickname));

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean login(String id, String password) {
        if (id.isEmpty() || password.isEmpty()) {
            System.out.println("ID와 비밀번호를 입력해주세요.");
            return false;
        }
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
                        if (l.getId().equals(id) && l.getPw().equals(password))
                            System.out.println("User Login");
                return true;
            } return false;
        } catch (Exception e) {
            return false;
        }
    }
}
