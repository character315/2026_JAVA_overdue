package game;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Component;
import java.awt.image.BufferedImage;

public class coin {

    private int x;
    private int y;

    // 金币移动速度
    private int speed = 4;

    // 当前动画帧
    private int frameIndex = 0;

    // 控制动画播放速度
    private int frameCount = 0;

    public coin(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // 金币移动和动画更新
    public void update() {

        // 向左移动
        x -= speed;

        // 每隔 5 次切换一张图片
        frameCount++;

        if (frameCount >= 5) {
            frameIndex++;

            if (frameIndex >= StaticValue.coinFrames.length) {
                frameIndex = 0;
            }

            frameCount = 0;
        }
    }

    // 绘制金币
    public void draw(Graphics g, Component observer) {

        BufferedImage image = StaticValue.coinFrames[frameIndex];

        if (image != null) {
            g.drawImage(image, x, y, 45, 45, observer);
        }
    }

    // 判断金币是否移出屏幕
    public boolean isOutOfScreen() {
        return x < -50;
    }

    // 获取金币碰撞区域，后面收集金币时会用到
    public Rectangle getBounds() {
        return new Rectangle(x + 5, y + 5, 25, 25);
    }
}
