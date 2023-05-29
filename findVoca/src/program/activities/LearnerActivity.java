package program.activities;

import program.ComponentFactory;
import program.MainActivity;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LearnerActivity extends JPanel {
    private MainActivity main;
    private ComponentFactory cf;
    private Image background;
    private JLabel nickLabel, vLabel;
    private String vocaName, nickSample = "닉네임";
    private int vocaSize;
    private JButton logoutBtn, createBtn, setBtn, learnBtn;
    private JScrollPane scrollPane;

    public LearnerActivity(MainActivity main/*, tcpClient client*/) {
        this.main = main;
        cf = new ComponentFactory();
        background = new ImageIcon(MainActivity.class.getResource("res/learnerBackground.png")).getImage();
        setOpaque(false);
        setLayout(null);
        setVisible(true);

        // 단어장 정보 불러오기 (임시 데이터 사용)
        String[] vocaNames = {"영단기 100", "토익 기출", "일본어 회화", "영단기 100", "토익 기출", "일본어 회화", "일본어 회화", "일본어 회화", "일본어 회화"};
        int[] vocaSizes = {2, 5, 6, 8, 100, 31, 11, 35, 66, 33};

        nickLabel = cf.createLabel(nickSample, SwingConstants.RIGHT, 295, 62, 127, 31, 34);
        add(nickLabel);

        vLabel = cf.createLabel("Vocabulary", 44, 198, 246, 41, 44);
        add(vLabel);

        setBtn = cf.createButton("res/btns/logoBtn.png", 440, 36, 126, 126, e -> {
            main.change("setActivity");
        });
        add(setBtn);

        logoutBtn = cf.createButton("res/btns/logoutBtn.png", 306, 105, 116, 27, e -> {
            main.change("initActivity");
            //세션 종료
        });
        add(logoutBtn);

        createBtn = cf.createButton("res/btns/createBtn.png", 473, 190, 88, 56, e -> {
            main.change("createVocabulary");
        });
        add(createBtn);

        DefaultListModel<String> vocaListModel = new DefaultListModel<>();
        JList<String> vocaList = new JList<>(vocaListModel);
        vocaList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        vocaList.setFont(new Font("twayair", Font.PLAIN, 36));
        vocaList.setOpaque(false);

        for (int i = 0; i < vocaNames.length; i++) {
            vocaName = vocaNames[i];
            vocaSize = vocaSizes[i];
            vocaListModel.addElement(vocaName + "   -   " + vocaSize + "개");
        }

        scrollPane = new JScrollPane(vocaList);
        scrollPane.setBounds(44, 260, 512, 520);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        add(scrollPane);

        vocaList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedIndex = vocaList.getSelectedIndex();
                if (selectedIndex != -1) {
                    String selectedVoca = vocaList.getSelectedValue();
                    System.out.println("Selected Index: " + selectedIndex);
                    System.out.println("Selected Vocabulary: " + selectedVoca);
                    main.change("ViewVocabulary");
                }
            }
        });

    }

    protected void paintComponent(Graphics g) {
        g.drawImage(background, 0, 0, 600, 772, null);
    }

}
