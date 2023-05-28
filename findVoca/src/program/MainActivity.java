package program;

import program.activities.initActivity;
import server.tcpClient;

import javax.swing.*;

public class MainActivity extends JFrame {
    public static final int GAME_WIDTH = 600;
    public static final int GAME_HEIGHT = 800;
    static tcpClient client;
    initActivity initActivity;

    public static void main(String[] args) {
        MainActivity main = new MainActivity();

        main.setTitle("Find Voca!");
        main.setSize(GAME_WIDTH, GAME_HEIGHT);
        main.setResizable(false);
        main.setLocationRelativeTo(null);
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        main.client = new tcpClient();
        main.initActivity = new initActivity(main, client);

        main.add(main.initActivity);
        main.setVisible(true);
    }

    public void change(String panelName) {
        if (panelName.equals("initActivity")) {
            getContentPane().removeAll();
            getContentPane().add(initActivity);
            revalidate();
            repaint();
        }
    }
}

