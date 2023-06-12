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
    private JPanel wordPanel;

    private int nextLine;

    public ViewVocabulary(MainActivity main/*, Client client*/) {
        this.main = main;
        cf = new ComponentFactory();
        background = new ImageIcon(MainActivity.class.getResource("res/createBackground.png")).getImage();
        nextLine = 0;
        setOpaque(false);
        setLayout(null);
        setVisible(true);

        //단어장 정보 불러오기
        String vocaName = "영단기 100";
        String[] words = {"apple", "banana"};
        String[] means = {"사과", "바나나"};

        backBtn = cf.createButton("res/btns/smallBackBtn.png", 21, 19, 23 ,35, e -> {
            main.change("learnerActivity");
        });
        add(backBtn);

        vLabel = cf.createLabel(vocaName, 81, 20, 454, 41, 40);
        add(vLabel);

        wordLabel = cf.createLabel(words[0], 0, nextLine, 0, 0, 28);
        add(wordLabel);
        meanLabel = cf.createLabel(means[0], 0, nextLine, 0, 0, 28);
        add(meanLabel);
        checkedLabel = cf.createLabel(isLearned(), 0, nextLine, 0, 0, 28);
        add(checkedLabel);
        deleteWordBtn = cf.createButton("res/btns/deleteWordBtn.png", 0, nextLine, 0, 0, e -> {
            //단어장 내의 해당 단어만 삭제
        });
        add(deleteWordBtn);


        editBtn = cf.createButton("res/btns/editBtn.png", 0, 664, 0, 0, e -> {
            main.editVocabulary = new EditVocabulary(main);
            main.change("editVocabulary");
        });

        deleteBtn = cf.createButton("res/btns/deleteBtn.png", 0, 664, 0, 0, e -> {
            //단어장 db내의 해당 단어장 삭제
            //어떤 학습자의 'vocaName'
        });

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
