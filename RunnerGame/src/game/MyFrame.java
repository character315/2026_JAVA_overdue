package game;

import java.awt.Graphics;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.Rectangle;
import java.util.*;
import java.awt.Font;



public class MyFrame extends JFrame {

    private BackGround nowBg;
    private dog player;
    private int bgX = 0;
    private List<coin> coins = new ArrayList<>();
    private int coinTimer = 0;
    private int score = 0;
    private int highScore = 0;
    private List<Obstacle> obstacles = new ArrayList<>();
    private int obstacleTimer = 0;
    private boolean gameOver = false;

    public MyFrame() {
        StaticValue.init();
        nowBg = new BackGround();
        player = new dog(10, 215);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                int w = getWidth();
                int h = getHeight();

                // 画背景
                g.drawImage(nowBg.getBgImage(),
                            bgX, 0, w, h, this);
                g.drawImage(nowBg.getBgImage(),
                        bgX + w, 0, w, h, this);
                g.setFont(new Font("Arial", Font.BOLD, 30));
                g.drawString("Score:"+score,getWidth()-120,40);
               
                //画小狗
                g.drawImage(player.getShow(),
                            player.getX(), player.getY(), this);
               
                //画障碍物
                for (Obstacle obstacle : obstacles) {
                    obstacle.draw(g, this);
                }
                //画金币
                for (coin coin : coins) {
                    coin.draw(g, this);
                }
                if (gameOver) {
                    g.setColor(new java.awt.Color(0, 0, 0, 140));
                    g.fillRect(0, 0, w, h);

                    g.setColor(java.awt.Color.WHITE);
                    g.setFont(new Font("Arial", Font.BOLD, 50));
                    g.drawString("Game Over", w / 2 - 130, h / 2 - 30);

                    g.setFont(new Font("Arial", Font.BOLD, 30));
                    g.drawString("Score: " + score, w / 2 - 110, h / 2 + 20);
                    
                }
            }
        };

        add(panel);

        setTitle("멍멍 대시");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    player.jump();
                }
            }
        });

        new Timer(20, e -> {
        	if (gameOver) {
        	    panel.repaint();
        	    
        	    return;
        	}
        	int w = panel.getWidth();

            // 背景向左移动
            bgX -= 4;
            if (bgX <= -w) {
                bgX += w;
            }

            // 更新小狗
            player.update();
            
            // 每 100 次生成一枚金币
            coinTimer++;

            if (coinTimer >= 100) {
                coins.add(new coin(panel.getWidth(), 150));
                coinTimer = 0;
            }

            // 更新金币
            Iterator<coin> iterator = coins.iterator();

            // 获取小狗的碰撞范围
            int imgW = player.getShow().getWidth(panel);
            int imgH = player.getShow().getHeight(panel);

            int padLeft = 85, padRight = 85, padTop = 70, padBottom = 70;

            Rectangle dogBounds = new Rectangle(
                player.getX() + padLeft,
                player.getY() + padTop,
                imgW - padLeft - padRight,
                imgH - padTop - padBottom
            );

            while (iterator.hasNext()) {

                coin coin = iterator.next();
                coin.update();

                // 判断小狗是否碰到金币
                if (dogBounds.intersects(coin.getBounds())) {
                	score += 10;
                    iterator.remove();
                } else if (coin.isOutOfScreen()) {
                    iterator.remove();
                }
            }
         // 每隔一段时间生成一个障碍物
            obstacleTimer++;

             if (obstacleTimer >= 150) {

                int obstacleY = 370;

                int index = (int)(Math.random() * StaticValue.obstacleImages.length);

                obstacles.add(
                    new Obstacle(
                        panel.getWidth(),
                        obstacleY,
                        StaticValue.obstacleImages[index]
                    )
                );

                obstacleTimer = 0;
            }

            // 更新障碍物
             Iterator<Obstacle> obstacleIterator = obstacles.iterator();

             while (obstacleIterator.hasNext()) {

                 Obstacle obstacle = obstacleIterator.next();

                 obstacle.update();

                 // 检查小狗是否撞到障碍物
                 if (dogBounds.intersects(obstacle.getBounds())) {
                	    gameOver = true;
                	    if (score > highScore) highScore = score;
                	}

                 // 移出屏幕后删除
                 if (obstacle.isOutOfScreen()) {
                     obstacleIterator.remove();
                 }
             }
            // 重新绘制
            panel.repaint();
            

        }).start();

        setVisible(true);
        requestFocusInWindow();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MyFrame());
    }
}
