package program.activities;

import program.ComponentFactory;
import program.MainActivity;
import server.Client;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CreateVocabulary extends JPanel {
    private ComponentFactory cf;
    private Image background;
    private JTextField vField;
    private JButton backBtn, addWordBtn, saveBtn;
    private JPanel addFieldPanel, savePanel;
    private int nextLine;
    private List<JTextField> wordFields, meanFields;
    private JOptionPane notFound;


    public CreateVocabulary(MainActivity main, Client client) {
        cf = new ComponentFactory();
        background = new ImageIcon(MainActivity.class.getResource("res/createBackground.png")).getImage();
        nextLine = 0;
        wordFields = new ArrayList<>();
        meanFields = new ArrayList<>();
        setOpaque(false);
        setLayout(null);
        setVisible(true);
        notFound = new JOptionPane();

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
            String vocaName = vField.getText();
            for (int i = 0; i < wordFields.size(); i++) {
                String word = wordFields.get(i).getText();
                String mean = meanFields.get(i).getText();
                client.send("@create@" + client.getLearnerID() + "@" + vocaName + "@" + word + "@" + mean);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }

            if (client.getCreateResult()) {
                notFound.showMessageDialog(null, "단어장이 생성됐습니다.");
                main.learnerActivity = new LearnerActivity(main, client);
                main.change("learnerActivity");
            } else {
                notFound.showMessageDialog(null, "단어장 생성에 실패했습니다.");
            }
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
        wordFields.add(wordField);
        meanFields.add(meanField);

        addWordBtn.setBounds(34, nextLine, 519, 41);
        nextLine += 50;

        addFieldPanel.setPreferredSize(new Dimension(600, nextLine + 50));
        revalidate();
        repaint();
    }

    protected void paintComponent(Graphics g) {
        g.drawImage(background, 0, 0, 585, 820, null);
    }
}
