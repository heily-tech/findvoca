package program.activities;

import program.ComponentFactory;
import program.MainActivity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SetActivity extends JPanel {
    private MainActivity main;
    private ComponentFactory cf;
    private JButton backBtn, withdrawal;
    private Image background ;

    public SetActivity(MainActivity main) {
        this.main = main;
        cf = new ComponentFactory();
        background = new ImageIcon(MainActivity.class.getResource("res/SignUpBackground.png")).getImage();
        setOpaque(false);
        setLayout(null);
        setVisible(true);

        backBtn = cf.createButton("res/btns/backBtn.png", 18, 22, 149, 31, e -> {
            main.change("learnerActivity");
        });
        add(backBtn);

        withdrawal = cf.createButton("res/btns/withdrawalBtn.png", 230, 441, 132, 31, e -> {
            // 세션 종료하고 회원 정보 삭제하기
            main.change("initActivity");
        });
        add(withdrawal);
    }
    protected void paintComponent(Graphics g) {
        g.drawImage(background, 0, 0, 600, 772, null);
    }
}
