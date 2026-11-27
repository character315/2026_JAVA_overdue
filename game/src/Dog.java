package game;

public class Dog {
    private static final int GROUND_Y = 220;
    private int y = GROUND_Y;
    private int velocityY = 0;
    private int jumpCount = 0;

    public void jump() {
        if (jumpCount < 2) {
            velocityY = -15;
            jumpCount++;
        }
    }

   public void update() {
        y += velocityY;
        velocityY += 1;
        if (y >= GROUND_Y) {
            y = GROUND_Y;
            velocityY = 0;
            jumpCount = 0;
        }
    }
    public int getY() {
        return y;
    }
}