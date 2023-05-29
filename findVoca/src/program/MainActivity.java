package program;

import program.activities.*;
import server.tcpClient;

import javax.swing.*;

public class MainActivity extends JFrame {
    public static final int GAME_WIDTH = 600;
    public static final int GAME_HEIGHT = 800;
    static tcpClient client;
    InitActivity initActivity;
    SignUpActivity signUpActivity;
    LearnerActivity learnerActivity;
    SetActivity setActivity;
    CreateVocabulary createVocabulary;
    ViewVocabulary viewVocabulary;
    EditVocabulary editVocabulary;

    public static void main(String[] args) {
        MainActivity main = new MainActivity();

        main.setTitle("Find Voca!");
        main.setSize(GAME_WIDTH, GAME_HEIGHT);
        main.setResizable(false);
        main.setLocationRelativeTo(null);
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        main.client = new tcpClient();
        main.initActivity = new InitActivity(main, client);
        main.signUpActivity = new SignUpActivity(main, client);
        main.learnerActivity = new LearnerActivity(main);
        main.setActivity = new SetActivity(main);
        main.createVocabulary = new CreateVocabulary(main);
        main.viewVocabulary = new ViewVocabulary(main);
        main.editVocabulary = new EditVocabulary(main);

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

        revalidate();
        repaint();
    }
}

