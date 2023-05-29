package program.activities;

import program.MainActivity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class EditActivity extends JPanel {

    //단어장 정보 불러오기

    String vocaName = "영단기 100";
    String[] words = {"apple", "banana"};
    String[] means = {"사과", "바나나"};



/*
    vLabel = new JLabel(vocaName);
        vLabel.setFont(new Font("twayair",Font.PLAIN, 40));
        vLabel.setHorizontalAlignment(JLabel.CENTER);
        vLabel.setBounds(81, 20, 454, 41);
    add(vLabel);

        for (String s : words) {
        nextLine += 100;
        wordLabel = new JLabel(s);
        wordLabel.setFont(new Font("twayair", Font.PLAIN, 37));
        wordLabel.setHorizontalAlignment(JLabel.CENTER);
        wordLabel.setBounds(44, nextLine, 104, 35);
        add(wordLabel);
    }

    wordLabel = new JLabel(word);
        wordLabel.setFont(new Font("twayair", Font.PLAIN, 37));
        wordLabel.setHorizontalAlignment(JLabel.CENTER);
        wordLabel.setBounds(44, nextLine, 104, 35);
    add(wordLabel);

    partLabel = new JLabel("|");
        partLabel.setFont(new Font("twayair", Font.PLAIN, 37));
        partLabel.setBounds(179, nextLine, 12, 35);
    add(partLabel);

    meanField = new JLabel(mean);
        meanField.setFont(new Font("twayair", Font.PLAIN, 37));
//        meanField.setHorizontalAlignment(JLabel.CENTER);
        meanField.setBounds(220, nextLine, 340, 35);
    add(meanField);

    */
}

/*
package program.activities;

        import program.MainActivity;

        import javax.swing.*;
        import java.awt.*;
        import java.awt.event.*;

public class createActivity extends JPanel {
    private MainActivity main;
    private Image background;
    private JTextField vField;
    private JButton backBtn, addWordBtn, saveBtn;
    private java.util.List<FieldGroup> fieldGroups;
    private int nextLine;
    JTextField vField, wordField, meanField;
    JLabel partLabel;

    int nextLine = 100;

    public createActivity(MainActivity main) {
        background = new ImageIcon(MainActivity.class.getResource("res/createBackground.png")).getImage();

        this.main = main;
        setOpaque(false);
        setLayout(null);
        setVisible(true);

        backBtn = new JButton();
        backBtn.setIcon(new ImageIcon(MainActivity.class.getResource("res/btns/smallBackBtn.png")));
        backBtn.setBorderPainted(false);
        backBtn.setContentAreaFilled(false);
        backBtn.addActionListener(e -> {
            main.change("learnerActivity");
            vField.setText("");
        });
        backBtn.setBounds(21, 19, 23, 35);
        add(backBtn);

        vField = new JTextField("단어장 이름을 입력하세요.");
        vField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                vField.setText("");
            }
        });
        vField.setFont(new Font("twayair", Font.PLAIN, 40));
        vField.setBorder(BorderFactory.createEmptyBorder());
        vField.setOpaque(false);
        vField.setHorizontalAlignment(JLabel.CENTER);
        vField.setBounds(81, 20, 454, 41);
        add(vField);

        wordField = new JTextField("어휘");
        wordField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                wordField.setText("");
            }
        });
        wordField.setFont(new Font("twayair", Font.PLAIN, 37));
        wordField.setBorder(BorderFactory.createEmptyBorder());
        wordField.setOpaque(false);
        wordField.setHorizontalAlignment(JLabel.CENTER);
        wordField.setBounds(44, nextLine, 104, 35);
        add(wordField);

        partLabel = new JLabel("|");
        partLabel.setFont(new Font("twayair", Font.PLAIN, 37));
        partLabel.setBounds(179, nextLine, 12, 35);
        add(partLabel);

        meanField = new JTextField("뜻");
        meanField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                meanField.setText("");
            }
        });
        meanField.setFont(new Font("twayair", Font.PLAIN, 37));
        meanField.setBorder(BorderFactory.createEmptyBorder());
        meanField.setOpaque(false);
        meanField.setBounds(220, nextLine, 340, 35);
        add(meanField);

        addWordBtn = new JButton();
        addWordBtn.setIcon(new ImageIcon(MainActivity.class.getResource("res/btns/addWordBtn.png")));
        addWordBtn.setRolloverIcon(new ImageIcon(MainActivity.class.getResource("res/btns/addWordBtn2.png")));
        addWordBtn.setBorderPainted(false);
        addWordBtn.setContentAreaFilled(false);
        addWordBtn.addActionListener(e -> {
            //addField
            nextLine += 50;
        });
        addWordBtn.setBounds(46, nextLine+50, 519, 41);
        add(addWordBtn);
        System.out.println(nextLine);
    }

    protected void paintComponent(Graphics g) {
        g.drawImage(background, 0, 0, 600, 772, null);
    }
}*/
