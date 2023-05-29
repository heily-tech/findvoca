package program.activities;

import com.sun.tools.javac.Main;
import program.ComponentFactory;
import program.MainActivity;

import javax.swing.*;
import java.awt.*;

public class ViewVocabulary extends JPanel {
    private MainActivity main;
    private ComponentFactory cf;
    private Image background;
    private JButton learnBtn, editBtn, deleteBtn, deleteWordBtn, backBtn;
    private JLabel vLabel, wordLael, meanLabel, checkedLabel;

    private int nextLine;

    public ViewVocabulary(MainActivity main) {
        this.main = main;
        cf = new ComponentFactory();
        background = new ImageIcon(MainActivity.class.getResource("res/createBackground.png")).getImage();
        nextLine = 0;
        setOpaque(false);
        setLayout(null);
        setVisible(true);



        learnBtn = cf.createButton("res/btns/learnBtn.png", 200, 664, 201, 76, e -> {
            //main.change("");
            System.out.println("학습하기");
        });
        add(learnBtn);
    }

    protected void paintComponent(Graphics g) {
        g.drawImage(background, 0, 0, 600, 772, null);
    }
}
