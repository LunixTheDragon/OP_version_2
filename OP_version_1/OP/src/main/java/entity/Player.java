package entity;


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

}
