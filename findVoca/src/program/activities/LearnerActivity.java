package program.activities;

import program.ComponentFactory;
import program.MainActivity;
import server.Client;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LearnerActivity extends JPanel {
    private ComponentFactory cf;
    private Image background;
    private JLabel nickLabel, vLabel;
    private JButton logoutBtn, createBtn, setBtn;
    private JScrollPane scrollPane;
    private String learnerID, learnerNickname;
    private String[] vocaNames;

    public LearnerActivity(MainActivity main, Client client) {
        cf = new ComponentFactory();
        background = new ImageIcon(MainActivity.class.getResource("res/learnerBackground.png")).getImage();
        learnerID = client.getLearnerID();
        setOpaque(false);
        setLayout(null);
        setVisible(true);
        client.send("@learnerInfo@"+ learnerID);
        try {
            Thread.sleep(300);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        learnerNickname = client.getLearnerNickname();
        nickLabel = cf.createLabel(learnerNickname, SwingConstants.RIGHT, 295, 62, 127, 31, 34);
        add(nickLabel);

        vLabel = cf.createLabel("Vocabulary", 44, 198, 246, 41, 44);
        add(vLabel);

        setBtn = cf.createButton("res/btns/logoBtn.png", 440, 36, 126, 126, e -> {
            main.setActivity = new SetActivity(main, client);
            main.change("setActivity");
        });
        add(setBtn);

        logoutBtn = cf.createButton("res/btns/logoutBtn.png", 306, 105, 116, 27, e -> {
            main.change("initActivity");
            client.stopClient();
        });
        add(logoutBtn);

        createBtn = cf.createButton("res/btns/createBtn.png", 473, 190, 88, 56, e -> {
            main.createVocabulary = new CreateVocabulary(main);
            main.change("createVocabulary");
        });
        add(createBtn);


        DefaultListModel<String> vocaListModel = new DefaultListModel<>();
        JList<String> vocaList = new JList<>(vocaListModel);
        vocaList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        vocaList.setFont(new Font("twayair", Font.PLAIN, 36));
        vocaList.setOpaque(false);

        client.send("@learnerVoca@" + learnerID);
        try {
            Thread.sleep(300);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        vocaNames = client.getVocaNames();
        for (String v : vocaNames) {
            vocaListModel.addElement(v);
        }

        scrollPane = new JScrollPane(vocaList);
        scrollPane.setBounds(44, 260, 512, 480);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        add(scrollPane);

        vocaList.addListSelectionListener(e -> {
            main.viewVocabulary = new ViewVocabulary(main);
            if (!e.getValueIsAdjusting()) {
                int selectedIndex = vocaList.getSelectedIndex();
                if (selectedIndex != -1) {
                    String selectedVoca = vocaList.getSelectedValue();
                    System.out.println("Selected Index: " + selectedIndex);
                    System.out.println("Selected Vocabulary: " + selectedVoca);
                    main.change("viewVocabulary");
                }
            }
        });

    }

    protected void paintComponent(Graphics g) {
        g.drawImage(background, 0, 0, 600, 772, null);
    }

}
