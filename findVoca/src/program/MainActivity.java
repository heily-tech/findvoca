package program;

import program.activities.createActivity;
import program.activities.setActivity;
import program.activities.learnerActivity;
import program.activities.signUpActivity;
import program.activities.initActivity;
import server.tcpClient;

import javax.swing.*;

public class MainActivity extends JFrame {
    public static final int GAME_WIDTH = 600;
    public static final int GAME_HEIGHT = 800;
    static tcpClient client;
    initActivity initActivity;
    signUpActivity signUpActivity;
    learnerActivity learnerActivity;
    setActivity setActivity;
    createActivity createActivity;

    public static void main(String[] args) {
        MainActivity main = new MainActivity();

        main.setTitle("Find Voca!");
        main.setSize(GAME_WIDTH, GAME_HEIGHT);
        main.setResizable(false);
        main.setLocationRelativeTo(null);
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        main.client = new tcpClient();
        main.initActivity = new initActivity(main, client);
        main.signUpActivity = new signUpActivity(main, client);
        main.learnerActivity = new learnerActivity(main);
        main.setActivity = new setActivity(main);
        main.createActivity = new createActivity(main);

        main.add(main.initActivity);
        main.setVisible(true);
    }

    public void change(String panelName) {
        getContentPane().removeAll();

        if (panelName.equals("initActivity"))
            getContentPane().add(initActivity);
        else if (panelName.equals("signUpActivity"))
            getContentPane().add(signUpActivity);
        else if (panelName.equals("learnerActivity"))
            getContentPane().add(learnerActivity);
        else if (panelName.equals("setActivity"))
            getContentPane().add(setActivity);
        else if (panelName.equals("createActivity"))
            getContentPane().add(setActivity);

        revalidate();
        repaint();
    }
}

