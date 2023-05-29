package program;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ComponentFactory extends JPanel {

    public JButton createButton(String iconPath, int x, int y, int w, int h, ActionListener listener) {
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

    public JLabel createLabel(String iconPath, int x, int y, int w, int h) {
        JLabel label = new JLabel();
        label.setIcon(new ImageIcon(MainActivity.class.getResource(iconPath)));
        label.setBounds(x, y, w, h);
        return label;
    }

    public JLabel createLabel(String s, int x, int y, int w, int h, int size) {
        JLabel label = new JLabel(s);
        label.setFont(new Font("twayair", Font.PLAIN, size));
        label.setBounds(x, y, w, h);
        return label;
    }

    public JLabel createLabel(String text, int alignment, int x, int y, int w, int h, int size) {
        JLabel label = new JLabel(text);
        label.setHorizontalAlignment(alignment);
        label.setFont(new Font("twayair", Font.PLAIN, size));
        label.setBounds(x, y, w, h);
        return label;
    }

    public JTextField createTextField(int x, int y, int w, int h, int size) {
        JTextField textField = new JTextField();
        textField.setBorder(BorderFactory.createEmptyBorder());
        textField.setOpaque(false);
        textField.setFont(new Font("twayair", Font.PLAIN, size));
        textField.setBounds(x, y, w, h);
        return textField;
    }

    public JTextField createTextField(String s, int x, int y, int w, int h, int size) {
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

    public JPasswordField createPasswordField(int x, int y, int w, int h, int size) {
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBorder(BorderFactory.createEmptyBorder());
        passwordField.setOpaque(false);
        passwordField.setEchoChar('*');
        passwordField.setFont(new Font("twayair", Font.PLAIN, size));
        passwordField.setBounds(x, y, w, h);
        return passwordField;
    }
}
