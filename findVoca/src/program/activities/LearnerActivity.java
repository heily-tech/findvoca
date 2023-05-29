package program.activities;

import program.MainActivity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LearnerActivity extends JPanel {
    private MainActivity main;
    Image background = new ImageIcon(MainActivity.class.getResource("res/learnerBackground.png")).getImage();
    JLabel nickLabel, vLabel;
    JButton logoutBtn, createBtn, setBtn;
    String nickSample = "닉네임";


    public LearnerActivity(MainActivity main/*, tcpClient client*/) {
        this.main = main;
        setOpaque(false);
        setLayout(null);
        setVisible(true);

        nickLabel = new JLabel(nickSample);
        nickLabel.setHorizontalAlignment(JLabel.RIGHT);
        nickLabel.setFont(new Font("twayair", Font.PLAIN, 34));
        nickLabel.setBounds(295, 62, 127, 31);
        add(nickLabel);

        vLabel = new JLabel("Vocabulary");
        vLabel.setFont(new Font("twayair", Font.PLAIN, 44));
        vLabel.setBounds(44, 198, 246, 41);
        add(vLabel);

        setBtn = new JButton();
        setBtn.setIcon(new ImageIcon(MainActivity.class.getResource("res/btns/logoBtn.png")));
        setBtn.setBorderPainted(false);
        setBtn.setContentAreaFilled(false);
        setBtn.addActionListener(e -> {
                main.change("setActivity");
        });
        setBtn.setBounds(440, 36, 126, 126);
        add(setBtn);

        logoutBtn = new JButton();
        logoutBtn.setIcon(new ImageIcon(MainActivity.class.getResource("res/btns/logoutBtn.png")));
        logoutBtn.setBorderPainted(false);
        logoutBtn.setContentAreaFilled(false);
        logoutBtn.addActionListener(e -> {
                //세션 종료
                main.change("initActivity");
        });
        logoutBtn.setBounds(306, 105, 116, 27);
        add(logoutBtn);

        createBtn = new JButton();
        createBtn.setIcon(new ImageIcon(MainActivity.class.getResource("res/btns/createBtn.png")));
        createBtn.setRolloverIcon(new ImageIcon(MainActivity.class.getResource("res/btns/createBtn2.png")));
        createBtn.setBorderPainted(false);
        createBtn.setContentAreaFilled(false);
        createBtn.addActionListener(e -> {
                main.change("createActivity");
        });
        createBtn.setBounds(473, 190, 88, 56);
        add(createBtn);
    }

    protected void paintComponent(Graphics g) {
        g.drawImage(background, 0, 0, 600, 772, null);
    }

}
