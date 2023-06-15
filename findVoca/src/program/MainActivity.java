package program;

import program.activities.*;
import server.Client;

import javax.swing.*;
import java.nio.channels.SocketChannel;

public class MainActivity extends JFrame {
    public static final int GAME_WIDTH = 600;
    public static final int GAME_HEIGHT = 800;
    static Client client;
    static SocketChannel socket;
    InitActivity initActivity;
    SignUpActivity signUpActivity;
    public LearnerActivity learnerActivity;
    public SetActivity setActivity;
    public CreateVocabulary createVocabulary;
    public ViewVocabulary viewVocabulary;
    public EditVocabulary editVocabulary;
    public FindTypos findTypos;

    public static void main(String[] args) {
        MainActivity main = new MainActivity();

        main.setTitle("Find Voca!");
        main.setSize(GAME_WIDTH, GAME_HEIGHT);
        main.setResizable(false);
        main.setLocationRelativeTo(null);
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        main.client = new Client(socket);
        main.initActivity = new InitActivity(main, client);
        main.signUpActivity = new SignUpActivity(main, client);

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
        else if (panelName.equals("createVocabulary"))
            getContentPane().add(createVocabulary);
        else if (panelName.equals("viewVocabulary"))
            getContentPane().add(viewVocabulary);
        else if (panelName.equals("editVocabulary"))
            getContentPane().add(editVocabulary);
        else if (panelName.equals("findTypos"))
            getContentPane().add(findTypos);

        revalidate();
        repaint();
    }
}

