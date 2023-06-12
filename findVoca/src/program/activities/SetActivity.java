package program.activities;

import program.ComponentFactory;
import program.MainActivity;
import server.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SetActivity extends JPanel {
    private ComponentFactory cf;
    private JButton backBtn, withdrawal;
    private Image background;
    private String learnerID;
    private JOptionPane notFound;

    public SetActivity(MainActivity main, Client client) {
        cf = new ComponentFactory();
        background = new ImageIcon(MainActivity.class.getResource("res/SignUpBackground.png")).getImage();
        learnerID = client.getLearnerID();
        setOpaque(false);
        setLayout(null);
        setVisible(true);

        backBtn = cf.createButton("res/btns/backBtn.png", 18, 22, 149, 31, e -> {
            main.change("learnerActivity");
        });
        add(backBtn);

        withdrawal = cf.createButton("res/btns/withdrawalBtn.png", 230, 441, 132, 31, e -> {
            client.send("@withdrawal@" + learnerID);
            try {
                Thread.sleep(300);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

            if (client.getWithdrawalResult()) {
                client.stopClient();
                notFound.showMessageDialog(null, "계정이 삭제되었습니다.");
                main.change("initActivity");
            } else {
                notFound.showMessageDialog(null, "계정 삭제에 실패했습니다.");
            }
        });
        add(withdrawal);
    }


    public String getLearnerID() {
        return learnerID;
    }

    public void setLearnerID(String learnerID) {
        this.learnerID = learnerID;
    }

    protected void paintComponent(Graphics g) {
        g.drawImage(background, 0, 0, 600, 772, null);
    }
}
