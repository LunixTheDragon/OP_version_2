package entity;
import java.awt.*;

public class Player extends Entity {
    private int lives;
    private PlayerValues currentAction;

    public Player(float x, float y, int width, int height, int lives) {
        super(x, y, width, height);
        this.lives = lives;
        this.currentAction = PlayerValues.IDLE;
    }
    public void setAction(PlayerValues action){
        this.currentAction = action;
    }
    public PlayerValues getAction(){
        return currentAction;
    }
    public int getLives(){
        return lives;
    }
    public void decreaseLives(){
        if (this.lives > 0){
            lives--;
        }
    }
    public Rectangle getCollisionBounds(){
        return new Rectangle((int)x , (int)y , width , height );
    }
}
