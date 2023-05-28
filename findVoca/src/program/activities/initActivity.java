package program.activities;

import program.MainActivity;
import server.tcpClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class initActivity extends JPanel {
    Image background = new ImageIcon(MainActivity.class.getResource("res/initBackground.png")).getImage();
    private MainActivity main;
    JButton loginBtn, joinBtn;
    JLabel idLabel, pwLabel;
    JTextField idField;
    JPasswordField pwField;
    String password = "";
    String idSample = "admin", pwSample = "admin";
    JOptionPane notFound;

    public initActivity(MainActivity main, tcpClient client) {
        this.main = main;
        setOpaque(false);
        setLayout(null);
        notFound = new JOptionPane();
        client.startClient();

        loginBtn = new JButton();
        loginBtn.setIcon(new ImageIcon(MainActivity.class.getResource("res/btns/loginBtn.png")));
        loginBtn.setBorderPainted(false);
        loginBtn.setContentAreaFilled(false);
        loginBtn.setRolloverIcon(new ImageIcon(MainActivity.class.getResource("res/btns/loginBtn2.png")));
        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//            /*
                if(idField.getText().equals(idSample) && pwField.getText().equals(pwSample)) {
                    main.change("UserActivity");
                }
                else {
                    notFound.showMessageDialog(null, "ID/Password가 일치하지 않습니다.");
                    idField.setText(null);
                    pwField.setText(null);
                }
            }
//            */
            /*
                char[] pw = pwField.getPassword();
                for (char cha : pw) {
                    Character.toString(cha);
                    password += (password.equals("")) ? "" + cha + "" : "" + cha + "";
                }
                client.send("@login" + idField.getText() + "," + password);
                try {
                    Thread.sleep(300);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                if (client.getLoginResult()) {
                    main.change("UserActivity");
                    main.sfx("res/sfxs/select_with_reverb.wav");
                } else {
                    main.sfx("res/sfxs/error.wav");
                    notFound.showMessageDialog(null, "ID/Password가 일치하지 않습니다.");
                    idField.setText(null);
                    pwField.setText(null);
                }
            }
             */
        });
        loginBtn.setBounds(75, 600, 200, 80);
        add(loginBtn);

        joinBtn = new JButton();
        joinBtn.setIcon(new ImageIcon(MainActivity.class.getResource("res/btns/joinBtn.png")));
        joinBtn.setBorderPainted(false);
        joinBtn.setContentAreaFilled(false);
        joinBtn.setRolloverIcon(new ImageIcon(MainActivity.class.getResource("res/btns/joinBtn2.png")));
        joinBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //new SignUpActivity(main, client);
            }
        });
        joinBtn.setBounds(325, 600, 200, 75);
        add(joinBtn);

        idLabel = new JLabel("아이디");
//        idLabel.setIcon();
        idLabel.setFont(new Font("twayair", Font.PLAIN, 25));
        idLabel.setBounds(130, 360, 300, 50);
        add(idLabel);

        idField = new JTextField();
        idField.setBorder(BorderFactory.createEmptyBorder());
        idField.setFont(new Font("twayair", Font.PLAIN, 25));
        idField.setOpaque(false);
        idField.setBounds(220, 370, 180, 30);
        add(idField);

        pwLabel = new JLabel("비밀번호");
//        pwLabel.setIcon();
        pwLabel.setFont(new Font("twayair", Font.PLAIN, 25));
        pwLabel.setBounds(112, 460, 400, 50);
        add(pwLabel);

        pwField = new JPasswordField();
        pwField.setBorder(BorderFactory.createEmptyBorder());
        pwField.setOpaque(false);
        pwField.setEchoChar('*');
        pwField.setFont(new Font("twayair", Font.PLAIN, 25));
        pwField.setBounds(220, 470, 180, 30);
        add(pwField);
    }
    protected void paintComponent(Graphics g) {
        g.drawImage(background, 0, 0, 600, 800, null);
    }
}
