package game;

import javax.swing.*;
import java.awt.event.*;

public class MyFrame extends JFrame {
    private GameManager manager;
    private GamePanel panel;

    public MyFrame() {//构造方法
        //创建对象
        manager = new GameManager();
        panel = new GamePanel(manager);
        add(panel);

        //获取键盘
        panel.setFocusable(true);
        panel.addKeyListener(new KeyAdapter() {
    @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode()==KeyEvent.VK_SPACE) {
                manager.jump();
            }
        }
        });
    
        // 窗口设置
        setTitle("running game");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //时间循环
        Timer timer = new Timer(20,e -> {
            manager.update();
            if (manager.isGameOver()){
                ((Timer)e.getSource()).stop();
                panel.repaint();
                return;
            }
            panel.repaint();
        });
        timer.start();

        setVisible(true);
        panel.requestFocusInWindow();
    }


    public static void main(String[] args) {
        // SwingUtilities.invokeLater 创建窗口
        SwingUtilities.invokeLater(() -> {
            new MyFrame();
        });
        
    }
    

    
}