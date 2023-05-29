package program.activities;

import program.MainActivity;
import server.tcpClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUpActivity extends JPanel {
    private MainActivity main;
    JButton doBtn, backBtn;
    JLabel idLabel, pwLabel, nickLabel;
    JTextField idField, nickField;
    JPasswordField pwField;
    String password = "";
    JOptionPane notFound;
    Image background = new ImageIcon(MainActivity.class.getResource("res/SignUpBackground.png")).getImage();

    public SignUpActivity(MainActivity main, tcpClient client) {
        this.main = main;
        setOpaque(false);
        setLayout(null);
        setVisible(true);
        notFound = new JOptionPane();

        idLabel = new JLabel();
        idLabel.setIcon(new ImageIcon(MainActivity.class.getResource("res/btns/idBase.png")));
        idLabel.setBounds(139, 417, 340, 31);
        add(idLabel);
        idField = new JTextField();
        idField.setBorder(BorderFactory.createEmptyBorder());
        idField.setOpaque(false);
        idField.setFont(new Font("twayair", Font.PLAIN, 25));
        idField.setBounds(274, 415, 207, 30);
        add(idField);

        pwLabel = new JLabel();
        pwLabel.setIcon(new ImageIcon(MainActivity.class.getResource("res/btns/pwBase.png")));
        pwLabel.setBounds(101, 483, 378, 31);
        add(pwLabel);
        pwField = new JPasswordField();
        pwField.setBorder(BorderFactory.createEmptyBorder());
        pwField.setOpaque(false);
        pwField.setEchoChar('*');
        pwField.setFont(new Font("twayair", Font.PLAIN, 25));
        pwField.setBounds(274, 483, 207, 30);
        add(pwField);

        nickLabel = new JLabel();
        nickLabel.setIcon(new ImageIcon(MainActivity.class.getResource("res/btns/nickBase.png")));
        nickLabel.setBounds(139, 548, 340, 31);
        add(nickLabel);
        nickField = new JTextField();
        nickField.setBorder(BorderFactory.createEmptyBorder());
        nickField.setOpaque(false);
        nickField.setFont(new Font("twayair", Font.PLAIN, 25));
        nickField.setBounds(274, 548, 207, 30);
        add(nickField);

        doBtn = new JButton();
        doBtn.setIcon(new ImageIcon(MainActivity.class.getResource("res/btns/joinBtn.png")));
        doBtn.setBorderPainted(false);
        doBtn.setContentAreaFilled(false);
        doBtn.setRolloverIcon(new ImageIcon(MainActivity.class.getResource("res/btns/joinBtn2.png")));
        doBtn.setBounds(200, 644, 201, 76);
        doBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
            }
        });
        add(doBtn);

        backBtn = new JButton();
        backBtn.setIcon(new ImageIcon(MainActivity.class.getResource("res/btns/backBtn.png")));
        backBtn.setBorderPainted(false);
        backBtn.setContentAreaFilled(false);
        backBtn.setBounds(18,22, 149, 31);
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.change("initActivity");
            }
        });
        add(backBtn);
    }
    protected void paintComponent(Graphics g) {
        g.drawImage(background, 0, 0, 600, 772, null);
    }
}
