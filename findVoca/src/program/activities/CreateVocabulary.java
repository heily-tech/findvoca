package program.activities;

import program.ComponentFactory;
import program.MainActivity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CreateVocabulary extends JPanel {
    private MainActivity main;
    private ComponentFactory cf;
    private Image background;
    private JTextField vField;
    private JButton backBtn, addWordBtn, saveBtn;
    private JPanel addFieldPanel;
    private JPanel savePanel;
    private int nextLine;

    public CreateVocabulary(MainActivity main) {
        this.main = main;
        cf = new ComponentFactory();
        background = new ImageIcon(MainActivity.class.getResource("res/createBackground.png")).getImage();
        nextLine = 0;
        setOpaque(false);
        setLayout(null);
        setVisible(true);

        backBtn = cf.createButton("res/btns/smallBackBtn.png", 21, 19, 23 ,35, e -> {
            main.change("learnerActivity");
        });
        add(backBtn);

        vField = cf.createTextField("단어장 이름을 입력하세요.", 81, 20, 454, 41, 40);
        add(vField);

        addFieldPanel = new JPanel();
        addFieldPanel.setLayout(null);
        JScrollPane scrollPane = new JScrollPane(addFieldPanel);
        scrollPane.setBounds(12, 74, 576, 570);
        scrollPane.setViewportBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        add(scrollPane);

        addWordBtn = cf.createButton("res/btns/addWordBtn.png", 34, nextLine, 519, 41, e-> {
            addField();
        });
        addFieldPanel.add(addWordBtn);
        addField();

        savePanel = new JPanel();
        savePanel.setBounds(0, 650, 600, 122);
        savePanel.setOpaque(false);
        savePanel.setLayout(null);
        add(savePanel);

        saveBtn = cf.createButton("res/btns/saveBtn.png", 220, 14, 152, 68, e -> {
            // 내용 저장해서 보내기
            System.out.println("SAVE");
        });
        savePanel.add(saveBtn);
    }

    private void addField() {
        JTextField wordField = cf.createTextField("어휘", 32, nextLine, 104, 35, 37);
        JLabel partLabel = cf.createLabel("|", 167, nextLine, 12, 35, 37);
        JTextField meanField = cf.createTextField("뜻", 208, nextLine, 340, 35, 37);

        addFieldPanel.add(wordField);
        addFieldPanel.add(partLabel);
        addFieldPanel.add(meanField);

        addWordBtn.setBounds(34, nextLine, 519, 41);
        nextLine += 50;

        addFieldPanel.setPreferredSize(new Dimension(600, nextLine + 50));
        revalidate();
        repaint();
    }

    protected void paintComponent(Graphics g) {
        g.drawImage(background, 0, 0, 600, 772, null);
    }
}
