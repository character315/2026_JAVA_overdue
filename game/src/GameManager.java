package game;
public class GameManager {
    //设定地面坐标 初始位置 重力 跳跃次数
    private int bgX = 0;
    private Dog dog = new Dog();
    private boolean gameOver = false;
    private Coin coin = new Coin();
    private Obstacle obstacle = new Obstacle();
    private int score = 0;

    //跳跃
    public void jump() {
        dog.jump();
    }
    //每帧更新
    public void update() {
        //角色
        dog.update();

        //背景
        bgX -= 4;
        if (bgX <= -900) {
            bgX += 900;
        }

        //障碍物
        obstacle.update();

        //金币
        coin.update();
        if (coin.getX() + 70 > 120 && coin.getX() < 240 && dog.getY() < 10) {
            score += 10;
            coin.reset();
        }

        //游戏结束
        if (obstacle.getX() + 90 > 220 && obstacle.getX() < 230 && dog.getY() > 140) {
            gameOver = true;
        }
    }

    public int getDogY() {
        return dog.getY();
    }

    public int getBgX() {
    return bgX;
    }

    public int getObstacleX() {
        return obstacle.getX();
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public int getCoinX() {
        return coin.getX();
    }

    public int getCoinY() {
        return coin.getY();
    }
    public int getScore() {
        return score;
    }
}