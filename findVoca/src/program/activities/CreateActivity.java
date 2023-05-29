package program.activities;

import program.MainActivity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CreateActivity extends JPanel {
    private MainActivity main;
    private Image background;
    private JTextField vField;
    private JButton backBtn, addWordBtn, saveBtn;
    private JPanel addFieldPanel;
    private JPanel savePanel;
    private int nextLine;

    public CreateActivity(MainActivity main) {
        this.main = main;
        background = new ImageIcon(MainActivity.class.getResource("res/createBackground.png")).getImage();
        nextLine = 0;
        setOpaque(false);
        setLayout(null);
        setVisible(true);

        backBtn = createButton("res/btns/smallBackBtn.png", 21, 19, 23 ,35, e -> {
            main.change("learnerActivity");
        });
        add(backBtn);

        vField = createTextField("단어장 이름을 입력하세요.", 81, 20, 454, 41, 40);
        add(vField);

        addFieldPanel = new JPanel();
        //addFieldPanel.setBounds(0, 100, 600, 550);
        //addFieldPanel.setOpaque(false);
        addFieldPanel.setLayout(null);
        //add(addFieldPanel);

        JScrollPane scrollPane = new JScrollPane(addFieldPanel);
        scrollPane.setBounds(12, 74, 576, 570);
        scrollPane.setViewportBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(true);
        scrollPane.setBackground(Color.WHITE);
        add(scrollPane);

        addWordBtn = createButton("res/btns/addWordBtn.png", 34, nextLine, 519, 41, e-> {
            addField();
        });
        addFieldPanel.add(addWordBtn);
        addField();

        savePanel = new JPanel();
        savePanel.setBounds(0, 650, 600, 122);
        savePanel.setOpaque(false);
        savePanel.setLayout(null);
        add(savePanel);

        saveBtn = createButton("res/btns/saveBtn.png", 220, 14, 152, 68, e -> {
            // 내용 저장해서 보내기
            System.out.println("SAVE");
        });
        savePanel.add(saveBtn);
    }

    private JButton createButton(String iconPath, int x, int y, int w, int h, ActionListener listener) {
        JButton btn = new JButton();
        btn.setIcon(new ImageIcon(MainActivity.class.getResource(iconPath)));
        String rollPath = iconPath.replace(".png", "2.png");
        if (getClass().getResource(rollPath) != null)
            btn.setRolloverIcon(new ImageIcon(MainActivity.class.getResource(rollPath)));
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.addActionListener(listener);
        btn.setBounds(x, y, w, h);
        return btn;
    }

    private JTextField createTextField(String s, int x, int y, int w, int h, int size) {
        JTextField field = new JTextField(s);
        field.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                field.setText("");
            }
        });
        field.setFont(new Font("twayair", Font.PLAIN, size));
        field.setBorder(BorderFactory.createEmptyBorder());
        field.setOpaque(false);
        field.setHorizontalAlignment(JLabel.CENTER);
        field.setBounds(x, y, w, h);
        return field;
    }

    private void addField() {
        JTextField wordField = createTextField("어휘", 32, nextLine, 104, 35, 37);
        JLabel partLabel = createLabel("|", 167, nextLine, 12, 35, 37);
        JTextField meanField = createTextField("뜻", 208, nextLine, 340, 35, 37);

        addFieldPanel.add(wordField);
        addFieldPanel.add(partLabel);
        addFieldPanel.add(meanField);

        addWordBtn.setBounds(34, nextLine, 519, 41);
        nextLine += 50;

        addFieldPanel.setPreferredSize(new Dimension(600, nextLine + 50));
        revalidate();
        repaint();
    }

    private JLabel createLabel(String s, int x, int y, int w, int h, int size) {
        JLabel label = new JLabel(s);
        label.setFont(new Font("twayair", Font.PLAIN, size));
        label.setBounds(x, y, w, h);
        return label;
    }

    protected void paintComponent(Graphics g) {
        g.drawImage(background, 0, 0, 600, 772, null);
    }
}
