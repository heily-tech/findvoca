package dbconn;

import program.Learner;

import javax.swing.plaf.nimbus.State;
import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                String query = "CREATE TABLE IF NOT EXISTS learner (" +
                        "id VARCHAR(30) PRIMARY KEY, " +
                        "nickname VARCHAR(30), " +
                        "password VARCHAR(30)" +
                        ")";

                stmt.executeUpdate(query);
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error Occurred.");
                System.exit(1);
            }
        } return conn;
    }

    public boolean login(String id, String password) {
        if (id.isEmpty() || password.isEmpty()) {
            System.out.println("ID와 비밀번호를 입력해주세요.");
            return false;
        }
        String query = "SELECT id FROM learner WHERE id= ? AND password= ?";
        ResultSet resultSet = null;
        try {
            preStmt = DBConnection.getConnection().prepareStatement(query);
            preStmt.setString(1, id);
            preStmt.setString(2, password);
            resultSet = preStmt.executeQuery();

            if (resultSet.next()) {
                for(Learner l : lList)
                    if (l.getId().equals(id) && l.getPw().equals(password))
                        System.out.println("User Login");
                return true;
            } return false;
        } catch (Exception e) {
            return false;
        }
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

        String query = "INSERT INTO learner(id, password, nickname) VALUES(?, ?, ?)";
        try {
            preStmt = DBConnection.getConnection().prepareStatement(query);
            preStmt.setString(1, id);
            preStmt.setString(2, password);
            preStmt.setString(3, nickname);
            preStmt.executeUpdate();
            lList.add(new Learner(id, password, nickname));

            String createTableQuery = "CREATE TABLE IF NOT EXISTS " + id + " (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "vocaName VARCHAR(30), " +
                    "word VARCHAR(30), " +
                    "mean VARCHAR(30), " +
                    "isLearned BOOLEAN" +
                    ")";

            Statement stmt = DBConnection.getConnection().createStatement();
            stmt.executeUpdate(createTableQuery);
            String[][] defaultVocabulary = {
                    {"기본 단어장", "resilient", "불굴의", "false"},
                    {"기본 단어장", "meticulous", "세심한", "false"},
                    {"기본 단어장", "eccentric", "별난", "false"},
                    {"기본 단어장", "ambiguous", "애매한", "false"},
                    {"기본 단어장", "indispensable", "필수적인", "false"},
                    {"기본 단어장", "exquisite", "정교한", "false"},
                    {"기본 단어장", "inquisitive", "탐구심이 강한", "false"},
                    {"기본 단어장", "myriad", "무수한", "false"},
                    {"기본 단어장", "prolific", "다작의", "false"},
                    {"기본 단어장", "novel", "새로운", "false"}
            };
            for (String[] word : defaultVocabulary) {
                String insertQuery = "INSERT INTO " + id  + "(vocaName, word, mean, isLearned) " +
                        "VALUES ('" + word[0] + "', '" + word[1] + "', '" + word[2] + "', " + word[3] + ")";
                stmt.executeUpdate(insertQuery);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean withdrawal(String id) {
        String dropTableQuery = "DROP TABLE IF EXISTS " + id;
        String deleteRecordQuery = "DELETE FROM learner WHERE id = '" + id + "';";

        try{
            Statement stmt = DBConnection.getConnection().createStatement();
            stmt.executeUpdate(dropTableQuery);
            stmt.executeUpdate(deleteRecordQuery);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getNickname(String id) {
        String query = "SELECT nickname FROM learner WHERE id = '" + id + "';";
        try {
            Statement stmt = DBConnection.getConnection().createStatement();
            ResultSet resultSet = stmt.executeQuery(query);

            if (resultSet.next())
                return resultSet.getString("nickname");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return query;
    }

    public List<String> getVocaNames(String id) {
        List<String> vocaNames = new ArrayList<>();

        String query = "SELECT DISTINCT vocaName FROM " + id;
        try {
            Statement stmt = DBConnection.getConnection().createStatement();
            ResultSet res = stmt.executeQuery(query);

            while (res.next()) {
                String vocaName = res.getString("vocaName");
                vocaNames.add(vocaName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vocaNames;
    }

    public List<String> getVocaWords(String id, String voca) {
        List<String> words = new ArrayList<>();

        String query = "SELECT word FROM " + id + " WHERE vocaName = '" + voca +"';";
        try {
            Statement stmt = DBConnection.getConnection().createStatement();
            ResultSet res = stmt.executeQuery(query);

            while (res.next()) {
                String word = res.getString("word");
                words.add(word);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return words;
    }
    public List<String> getVocaMeans(String id, String voca) {
        List<String> means = new ArrayList<>();

        String query = "SELECT mean FROM " + id + " WHERE vocaName = '" + voca + "';";
        try {
            Statement stmt = DBConnection.getConnection().createStatement();
            ResultSet res = stmt.executeQuery(query);

            while (res.next()) {
                String mean = res.getString("mean");
                means.add(mean);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return means;
    }

    public boolean deleteVoca(String id, String voca) {
        String query = "DELETE FROM " + id + " WHERE vocaName = '" + voca + "';";

        try{
            Statement stmt = DBConnection.getConnection().createStatement();
            stmt.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteWord(String id, String voca) {
        String query = "DELETE FROM " + id + " WHERE vocaName = '" + voca + "';";

        try{
            Statement stmt = DBConnection.getConnection().createStatement();
            stmt.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
