package program.activities;

import program.MainActivity;

import javax.swing.*;
import java.awt.*;

public class SetActivity extends JPanel {
    private MainActivity main;
    JButton backBtn, withdrawal;
    Image background = new ImageIcon(MainActivity.class.getResource("res/SignUpBackground.png")).getImage();

    public SetActivity(MainActivity main) {
        this.main = main;
        setOpaque(false);
        setLayout(null);
        setVisible(true);

        backBtn = new JButton();
        backBtn.setIcon(new ImageIcon(MainActivity.class.getResource("res/btns/backBtn.png")));
        backBtn.setBorderPainted(false);
        backBtn.setContentAreaFilled(false);
        backBtn.setBounds(18,22, 149, 31);
        backBtn.addActionListener(e -> {
            main.change("learnerActivity");
        });
        add(backBtn);

        withdrawal = new JButton();
        withdrawal.setIcon(new ImageIcon(MainActivity.class.getResource("res/btns/withdrawalBtn.png")));
        withdrawal.setBorderPainted(false);
        withdrawal.setContentAreaFilled(false);
        withdrawal.setBounds(230, 441, 132, 31);
        withdrawal.addActionListener(e -> {
            //세션 종료하고 회원 정보 삭제하기
        });
        add(withdrawal);
    }

    protected void paintComponent(Graphics g) {
        g.drawImage(background, 0, 0, 600, 772, null);
    }
}
