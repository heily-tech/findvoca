package program.activities;

import program.ComponentFactory;
import program.MainActivity;
import server.tcpClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class InitActivity extends JPanel {
    private Image background;
    private MainActivity main;
    private ComponentFactory cf;
    private JButton loginBtn, joinBtn;
    private JLabel idLabel, pwLabel;
    private JTextField idField;
    private JPasswordField pwField;
    String password = "";
    private JOptionPane notFound;
    private String idSample = "ad", pwSample = "ad";

    public InitActivity(MainActivity main, tcpClient client) {
        this.main = main;
        cf = new ComponentFactory();
        background = new ImageIcon(MainActivity.class.getResource("res/initBackground.png")).getImage();
        notFound = new JOptionPane();
        setOpaque(false);
        setLayout(null);
        client.startClient();

        loginBtn = cf.createButton("res/btns/loginBtn.png", 75, 600, 200, 80, e -> {
            if (idField.getText().equals(idSample) && new String(pwField.getPassword()).equals(pwSample)) {
                main.change("learnerActivity");
            } else {
                notFound.showMessageDialog(null, "ID/Password가 일치하지 않습니다.");
            }
        });
        add(loginBtn);

        joinBtn = cf.createButton("res/btns/joinBtn.png",  325, 600, 200, 75, e -> {
            main.change("signUpActivity");
        });
        add(joinBtn);

        idLabel = cf.createLabel("res/btns/idBase.png", 149, 417, 340, 31);
        add(idLabel);

        idField = cf.createTextField(280, 417, 215, 30, 25);
        add(idField);

        pwLabel = cf.createLabel("res/btns/pwBase.png", 112, 480, 400, 50);
        add(pwLabel);

        pwField = cf.createPasswordField(280, 480, 215, 30, 25);
        add(pwField);
    }

    protected void paintComponent(Graphics g) {
        g.drawImage(background, 0, 0, 600, 772, null);
    }
}
