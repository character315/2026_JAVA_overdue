package game;

public class Obstacle {
    private int x = 900;
    private int speed = 6;
    

    public void update() {
        x -= speed;
        if (x < -50) {
            x = 900;
        }
    }

    public int getX() {
        return x;
    }
}