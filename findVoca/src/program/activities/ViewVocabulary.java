package program.activities;

import program.ComponentFactory;
import program.MainActivity;
import server.Client;

import javax.swing.*;
import java.awt.*;

public class ViewVocabulary extends JPanel {
    private MainActivity main;
    private ComponentFactory cf;
    private Image background;
    private JButton learnBtn, editBtn, deleteBtn, deleteWordBtn, backBtn;
    private JLabel vLabel, wordLabel, meanLabel, checkedLabel;
    private String words[], means[];
    private JOptionPane notFound;


    private int nextLine;

    public ViewVocabulary(MainActivity main, Client client) {
        this.main = main;
        cf = new ComponentFactory();
        background = new ImageIcon(MainActivity.class.getResource("res/createBackground.png")).getImage();
        nextLine = 100;
        notFound = new JOptionPane();
        setOpaque(false);
        setLayout(null);
        setVisible(true);

        backBtn = cf.createButton("res/btns/smallBackBtn.png", 21, 19, 23 ,35, e -> {
            main.change("learnerActivity");
        });
        add(backBtn);

        vLabel = cf.createLabel(client.getVocaName(), 81, 20, 454, 41, 40);
        add(vLabel);

        client.send("@learnerWord@" + client.getLearnerID() + "@" + client.getVocaName());
        try {
            Thread.sleep(300);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        words = client.getVocaWords();
        means = client.getVocaMeans();

        for (int i = 0; i < words.length; i++) {
            wordLabel = cf.createLabel(words[i], 34, nextLine, 180, 35, 28);
            add(wordLabel);
            meanLabel = cf.createLabel(means[i], 220, nextLine, 180, 35, 28);
            add(meanLabel);
            checkedLabel = cf.createLabel(isLearned(), 465, nextLine, 25, 25, 28);
            add(checkedLabel);
            /*
            deleteWordBtn = cf.createButton("res/btns/deleteWordBtn.png", 535, nextLine, 26, 35, e -> {
                client.send("@deleteWord@" + client.getLearnerID() + "@" + client.getVocaName());
                try {
                    Thread.sleep(300);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                if (client.getDeleteWordResult()) {
                    notFound.showMessageDialog(null, "단어가 삭제되었습니다.");
                    main.change("learnerActivity");
                } else {
                    notFound.showMessageDialog(null, "단어가 삭제 되지 않았습니다.");
                }
            });
            add(deleteWordBtn);
             */
            nextLine += 50;
        }
/*
        editBtn = cf.createButton("res/btns/editBtn.png", 65, 664, 118, 76, e -> {
            main.editVocabulary = new EditVocabulary(main);
            main.change("editVocabulary");
        });
        add(editBtn);
*/
        deleteBtn = cf.createButton("res/btns/deleteBtn.png", 417, 664, 118, 76, e -> {
            client.send("@deleteVoca@" + client.getLearnerID() + "@" + client.getVocaName());
            try {
                Thread.sleep(300);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            if (client.getDeleteVocaResult()) {
                notFound.showMessageDialog(null, "단어장이 삭제되었습니다.");
                main.change("learnerActivity");
            } else {
                notFound.showMessageDialog(null, "단어장이 삭제 되지 않았습니다.");
            }
        });
        add(deleteBtn);

        learnBtn = cf.createButton("res/btns/learnBtn.png", 200, 664, 201, 76, e -> {
            //main.change("");
            System.out.println("학습하기");
        });
        add(learnBtn);

    }

    private String isLearned() {
        //학습여부 ㅇㅇ이면, 라벨 뭐로
        //학습 여부 ㄴㄴ이면, 라벨 뭐로
        return "";
    }
    protected void paintComponent(Graphics g) {
        g.drawImage(background, 0, 0, 600, 772, null);
    }
}
