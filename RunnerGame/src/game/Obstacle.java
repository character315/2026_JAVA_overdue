package game;

import java.awt.Graphics;
import java.awt.Component;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;

public class Obstacle {

    private int x;
    private int y;

    private int width = 65;
    private int height = 65;
    private int speed = 4;

    private BufferedImage image;

    public Obstacle(int x, int y, BufferedImage image) {
        this.x = x;
        this.y = y;
        this.image = image;
    }

    public void update() {
        x -= speed;
    }

    public void draw(Graphics g, Component observer) {
        if (image != null) {
            g.drawImage(image, x, y, width, height, observer);
        }
    }

    public boolean isOutOfScreen() {
        return x < -width;
    }
 // 获取障碍物的碰撞范围
    public Rectangle getBounds() {
        return new Rectangle(
            x + 5,
            y + 5,
            width - 10,
            height - 10
        );
    }
}
