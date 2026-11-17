package game;

import java.awt.image.BufferedImage;

public class BackGround {

    // 当前背景图片
    private BufferedImage bgImage;

    public BackGround() {
        bgImage = StaticValue.bg1;
    }

    public BufferedImage getBgImage() {
        return bgImage;
    }
}