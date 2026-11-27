package game;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class GamePanel extends JPanel{
    private GameManager manager;
    private Image bg;
    private Image dog;
    private Image obstacle;
    private Image coin;

    public GamePanel(GameManager manager){
        this.manager = manager;
        bg = new ImageIcon("src/images/bg1.png").getImage();
        dog = new ImageIcon("src/images/walk_01.gif").getImage();
        obstacle = new ImageIcon("src/images/grass.png").getImage();
        coin = new ImageIcon("src/images/coin_01.png").getImage();
        
    }
@Override
protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.drawImage(bg, manager.getBgX(), 0, getWidth(), getHeight(), this);
    g.drawImage(bg, manager.getBgX() + getWidth(), 0, getWidth(), getHeight(), this);
    g.drawImage(dog, 100, manager.getDogY(), this);
    g.drawImage(obstacle, manager.getObstacleX(),330,90,90, this);
    g.drawImage(coin, manager.getCoinX(), manager.getCoinY(), 60, 60, this);
    g.setFont(new Font("Arial", Font.BOLD, 36));
    g.drawString("Score: " + manager.getScore(), 20, 50);

    if (manager.isGameOver()) {
    g.setFont(new Font("Arial", Font.BOLD, 60));
    g.drawString("GAME OVER", 300, 250);               // 写 Game Over

    g.setFont(new Font("Arial", Font.BOLD, 30));
    g.drawString("Final Score: " + manager.getScore(), 320, 310);   // 用 manager 的 getter 取分数
    }
}
}