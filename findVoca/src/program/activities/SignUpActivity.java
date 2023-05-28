package program.activities;

import program.MainActivity;
import server.tcpClient;

import javax.swing.*;
import java.awt.*;

public class SignUpActivity extends JFrame {
    JButton doBtn, backBtn;
    JLabel idLabel, pwLabel;
    JTextField idField;
    JPasswordField pwField;
    String password = "";
    JOptionPane notFound;

    public SignUpActivity(MainActivity main, tcpClient client) {
        Color c = new Color(231, 204, 157);
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(c);
        btnPanel.setLayout(null);
        notFound = new JOptionPane();

        idLabel = new JLabel("아이디");
        idLabel.setFont(new Font("twayair", Font.PLAIN, 25));
        idLabel.setBounds(138, 415, 111, 35);
        add(idLabel);
        idField = new JTextField();
        idField.setBorder(BorderFactory.createEmptyBorder());
        idField.setOpaque(false);
        idField.setFont(new Font("twayair", Font.PLAIN, 25));
        //idField.setBounds();
    }

}
