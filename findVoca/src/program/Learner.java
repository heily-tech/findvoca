package program;

public class Learner {
    private String id;
    private String pw;
    private String nickname;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Learner(String id, String pw, String nick) {
        this.id = id;
        this.pw = pw;
        this.nickname = nick;
    }
}
