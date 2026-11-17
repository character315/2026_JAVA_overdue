
package game;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Image;
import javax.swing.ImageIcon;


public class StaticValue {

    // 背景图片
    public static BufferedImage bg1;

    // 小狗图片
    public static Image walk_01;
    
    //金币
    public static BufferedImage[] coinFrames = new BufferedImage[7];
    
    //障碍物
    public static BufferedImage[] obstacleImages = new BufferedImage[2];

    // 图片文件夹路径
    public static String path =
            System.getProperty("user.dir") + "/src/images/";

    // 读取图片
    private static BufferedImage load(String name) {
        File file = new File(path + name);

        try {
            return ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 初始化图片
    public static void init() {
        bg1 = load("bg2.png");
        walk_01 = new ImageIcon(path + "walk_01.gif").getImage();
        
        for (int i = 0; i < coinFrames.length; i++) {
            coinFrames[i] = load(
                String.format("coin_%02d.png", i + 1)
            );
        }
        String[] obstacleNames = {"grass.png","wood.png"};
        	for (int i = 0; i < obstacleNames.length; i++) {
        	    obstacleImages[i] = load(obstacleNames[i]);
        	}
    }
}
