package pet;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

// 화면 표시와 메뉴 생성만 담당한다.
public class PetView extends JFrame {
    private final ImageIcon jumpIcon = loadIcon("/img/jump.gif");
    private final ImageIcon danceIcon = loadIcon("/img/music-dance.gif");
    private final ImageIcon sleepIcon = loadIcon("/img/sleepy.gif");
    private final JLabel dogLabel = new JLabel(jumpIcon);

    private final JMenuItem idleItem = new JMenuItem("기본");
    private final JMenuItem danceItem = new JMenuItem("댄스");
    private final JMenuItem sleepItem = new JMenuItem("휴식");
    private final JMenuItem exitItem = new JMenuItem("종료");

    public PetView() {
        setTitle("Desktop Pet");
        setSize(300, 300);
        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 0));
        setAlwaysOnTop(true);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        add(dogLabel);

        JPopupMenu menu = new JPopupMenu();
        menu.add(idleItem);
        menu.add(danceItem);
        menu.add(sleepItem);
        menu.addSeparator();
        menu.add(exitItem);
        dogLabel.setComponentPopupMenu(menu);
    }

    private ImageIcon loadIcon(String path) {
        URL url = getClass().getResource(path);
        if (url == null) {
            throw new IllegalStateException("Missing image: " + path);
        }
        return new ImageIcon(url);
    }

    void updateAnimation(PetState state) {
        switch (state) {
            case IDLE: dogLabel.setIcon(jumpIcon); break;
            case DANCE: dogLabel.setIcon(danceIcon); break;
            case SLEEP: dogLabel.setIcon(sleepIcon); break;
        }
    }

    JLabel getDogLabel() {
        return dogLabel;
    }

    void bindMenuActions(Runnable idle, Runnable dance,
                         Runnable sleep, Runnable close) {
        idleItem.addActionListener(e -> idle.run());
        danceItem.addActionListener(e -> dance.run());
        sleepItem.addActionListener(e -> sleep.run());
        exitItem.addActionListener(e -> close.run());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                close.run();
            }
        });
    }
}
