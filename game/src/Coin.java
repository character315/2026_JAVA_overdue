package game;

public class Coin{
    private int x = 1300;
    private int y = 120;
    private int speed = 6;

    public void update() {
        x -= speed;
        if (x < -50) {
            x = 1300; 
        }
    }

    public void reset(){
        x = 1300;
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}