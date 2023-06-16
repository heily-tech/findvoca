package program.activities;

import program.ComponentFactory;
import program.MainActivity;
import server.Client;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class FindTypos extends JPanel {
    private ComponentFactory cf;
    private MainActivity main;
    private Image background;
    private JLabel vLabel;
    private JButton backBtn, prevBtn, nextBtn;
    private String[] words;
    private JList<String> wordList;
    private DefaultListModel<String> wordListModel;
    private int pointer, answerIndex;
    private JOptionPane notFound;

    public FindTypos(MainActivity main, Client client) {
        cf = new ComponentFactory();
        background = new ImageIcon(MainActivity.class.getResource("res/createBackground.png")).getImage();
        pointer = 0;
        notFound = new JOptionPane();
        setOpaque(false);
        setLayout(null);
        setVisible(true);

        backBtn = cf.createButton("res/btns/smallBackBtn.png", 21, 19, 23, 35, e -> {
            main.learnerActivity = new LearnerActivity(main, client);
            main.change("learnerActivity");
        });
        add(backBtn);

        vLabel = cf.createLabel(client.getVocaName(), SwingConstants.CENTER, 65, 20, 454, 41, 40);
        add(vLabel);

        words = client.getVocaWords();
        wordListModel = new DefaultListModel<>();
        wordList = new JList<>(wordListModel);
        wordList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        wordList.setFont(new Font("twayair", Font.PLAIN, 36));
        wordList.setOpaque(false);
        JScrollPane scrollPane = new JScrollPane(wordList);
        scrollPane.setBounds(30, 110, 525, 500);
        add(scrollPane);
        for (int i = 0; i < words.length; i++) {
            wordListModel.addElement("0");
        }
        performSequence(words[pointer]);

        prevBtn = cf.createButton("res/btns/prevBtn.png", 21, 649, 118, 76, e -> {
            decreasePointer();
        });
        add(prevBtn);

        nextBtn = cf.createButton("res/btns/nextBtn.png", 450, 649, 118, 76, e -> {
            increasePointer();
        });
        add(nextBtn);
    }

    private void increasePointer() {
        pointer++;
        if (pointer >= words.length) {
            pointer = words.length - 1;
            notFound.showMessageDialog(null, "수고하셨습니다.");
            main.change("learnerActivity");
        } else {
            performSequence(words[pointer]);
        }
    }

    private void decreasePointer() {
        pointer--;
        if (pointer < 0) {
            pointer = 0;
        } else {
            performSequence(words[pointer]);
        }
    }

    private void performSequence(String word) {
        for (int i = 0; i < 10; i++) {
            Random random = new Random();
            answerIndex = random.nextInt(10);
            System.out.println(answerIndex);
            if (i == answerIndex) {
                wordListModel.set(i, createTypo(word));
                System.out.println("answerIndex : " + answerIndex);
                continue;
            }
            wordListModel.set(i, word);
        }

        wordList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedIndex = wordList.getSelectedIndex();
                if (selectedIndex != -1) {
                    System.out.println(selectedIndex);
                    if (selectedIndex == answerIndex)
                        increasePointer();
                }
            }
        });
    }

    private String createTypo(String word) {
        char[] chars = word.toCharArray();
        Random random = new Random();

        boolean typoGenerated = false;
        while (!typoGenerated) {
            int index = random.nextInt(chars.length);

            char c = chars[index];
            if (c == 'e')
                chars[index] = random.nextBoolean() ? 'a' : 'o';
            else if (c == 'a')
                chars[index] = random.nextBoolean() ? 'e' : 'o';
            else if (c == 'i')
                chars[index] = random.nextBoolean() ? 'l' : 'j';
            else if (c == 'l')
                chars[index] = random.nextBoolean() ? 'i' : 'j';
            else if (c == 'j')
                chars[index] = random.nextBoolean() ? 'i' : 'l';
            else if (c == 'u')
                chars[index] = random.nextBoolean() ? 'o' : 'w';
            else if (c == 'o')
                chars[index] = random.nextBoolean() ? 'a' : 'e';
            else if (c == 'n')
                chars[index] = random.nextBoolean() ? 'h' : 'm';
            else if (c == 'h')
                chars[index] = random.nextBoolean() ? 'n' : 'b';
            typoGenerated = chars[index] != c;
        }
        return new String(chars);
    }

    protected void paintComponent(Graphics g) {
        g.drawImage(background, 0, 0, 585, 820, null);
    }

}
