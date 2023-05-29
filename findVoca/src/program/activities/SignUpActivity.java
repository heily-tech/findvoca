package program.activities;

import program.ComponentFactory;
import program.MainActivity;
import server.tcpClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUpActivity extends JPanel {
    private MainActivity main;
    private ComponentFactory cf;
    private JButton doBtn, backBtn;
    private JLabel idLabel, pwLabel, nickLabel;
    private JTextField idField, nickField;
    private JPasswordField pwField;
    private String password = "";
    private JOptionPane notFound;
    private Image background;

    public SignUpActivity(MainActivity main, tcpClient client) {
        this.main = main;
        cf = new ComponentFactory();
        background = new ImageIcon(MainActivity.class.getResource("res/SignUpBackground.png")).getImage();
        setOpaque(false);
        setLayout(null);
        setVisible(true);
        notFound = new JOptionPane();

        idLabel = cf.createLabel("res/btns/idBase.png", 139, 417, 340, 31);
        add(idLabel);
        idField = cf.createTextField(274, 415, 207, 30, 25);
        add(idField);

        pwLabel = cf.createLabel("res/btns/pwBase.png", 101, 483, 378, 31);
        add(pwLabel);
        pwField = cf.createPasswordField(274, 483, 207, 30, 25);
        add(pwField);

        nickLabel = cf.createLabel("res/btns/nickBase.png", 139, 548, 340, 31);
        add(nickLabel);
        nickField = cf.createTextField(274, 548, 207, 30, 25);
        add(nickField);

        doBtn = cf.createButton("res/btns/joinBtn.png", 200, 644, 201, 76, e -> {
            char[] pw = pwField.getPassword();
            for (char cha : pw) {
                Character.toString(cha);
                password += (password.equals("")) ? ""+cha+"" : ""+cha+"";
            }
            client.send("@signup" + idField.getText() + "," + password);

            try {
                Thread.sleep(300);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

            if (client.getSignupResult()) {
                notFound.showMessageDialog(null, "회원가입에 성공했습니다.");
                main.change("initActivity");
            } else {
                notFound.showMessageDialog(null, "ID/Password가 이미 존재합니다.");
                idField.setText(null);
                pwField.setText(null);
            }
        });
        add(doBtn);

        backBtn = cf.createButton("res/btns/backBtn.png", 18, 22, 149, 31, e -> {
            main.change("initActivity");
        });
        add(backBtn);

    }

    protected void paintComponent(Graphics g) {
        g.drawImage(background, 0, 0, 600, 772, null);
    }
}
