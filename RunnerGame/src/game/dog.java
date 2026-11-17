package game;

import java.awt.Image;
import java.awt.image.BufferedImage;

public class dog {

    // 小狗坐标
    private int x;
    private int y;
    
    private final int groundY;
    private int velocityY = 0;
    private int gravity = 1;
    private boolean jumping = false;


    // 小狗图片
    private Image show;
    
    public dog(int x, int y) {
        this.x = x;
        this.y = y;
        this.groundY = y;
        this.show = StaticValue.walk_01;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Image getShow() {
        return show;
    }
    //跳跃
    public void jump() {
    	    if (!jumping) {    
    	    	velocityY = -15; 
    	    	jumping = true;
    	        
    	    }
    	

    }
    public void update() {
    	if(jumping) {
    		//根据速度改变小狗位置
    		y = y +velocityY;
    		velocityY = velocityY + gravity;
    		//检查是否回到地面
    		if (y >= groundY) {
    			y = groundY;
    			velocityY = 0;
    			jumping  = false;
    			
    		}
    	}
    }
   
    
}























