package main;

import entity.Backround;
import entity.Player;
import entity.PlayerValues;
import entity.Products;
import windows.GameOverPanel;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class GameLogic {
    Player player;
    ArrayList<Products> products;
    Backround backround;
    GameGraphics gg;
    private int playerDir = -1; //IDLE
    private boolean moving = false;
    private BufferedImage goodProductImage, badProductImage;
    private int score;
    private boolean isGameOver = false;
    private long gameTime;
    private long lastSpawnTime;

    public void initialize() {
        player = new Player(230, 441, 64, 40, 3); //where is spawn player, y = 441 exact position of ground
        products = new ArrayList<>();
        backround = new Backround(0, 0, 600, 750);
        this.score = 0;
        this.gameTime = 0;
        this.lastSpawnTime = System.currentTimeMillis();
    }

    public void setGameGraphics(GameGraphics gg) {
        this.gg = gg;
        this.goodProductImage = gg.draw.getGoodProductImage();
        this.badProductImage = gg.draw.getBadProductImage();
    }

    public void updatePlayerAction(PlayerValues action) {
        player.setAction(action);
    }

    public void setDirection(int direction) {
        this.playerDir = direction;
        moving = true;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    private void setAni() {
        if (moving) {
            updatePlayerAction(PlayerValues.RUNNING);
        } else {
            updatePlayerAction(PlayerValues.IDLE);
        }
    }

    public void spawnInitialProducts() {
            spawnProduct();
    }

    public void stopMoving() {
        setMoving(false);
    }

    private void updatePos() {
        if (this.moving) {
            switch (this.playerDir) {
                case KeyEvent.VK_A, KeyEvent.VK_LEFT:
                    player.setX(player.getX() - 3); //Players speed
                    break;
                case KeyEvent.VK_D, KeyEvent.VK_RIGHT:
                    player.setX(player.getX() + 3);
                    break;
            }
            //screen wrapping logic
            if (player.getX() < 0){
                player.setX(gg.getWidth()); //If the player's x-coordinate (gg.draw.xMoving) is less than 0 , their position is set to the width of the game window
            }else if(player.getX() > gg.getWidth()){
                player.setX(0);
            }
            //reflects updated x position in gg
            gg.draw.xMoving = (int) player.getX();
        }
    }

    public void update() {
        if (player.getLives() <= 0 && !isGameOver){
            isGameOver = true;
            new GameOverPanel(score).setVisible(true);
            gg.dispose();
            return;
        }
        if (!isGameOver) {
            setAni();
            updatePos();
            updateProducts();
            checkCollisions();
            gameTime += 1;
            if (products.isEmpty()) {
                spawnProduct(); //ensures one product is spawned at a time
            }
        }
    }

    private void updateProducts() {
        int fallingSpeed = getFallingSpeed();
        for (int i = 0; i < products.size(); i++) {
            Products product = products.get(i);
            product.setY(product.getY() + fallingSpeed); //falling speed
            if (product.getPosition() >= 448) {   // they dont have exact right sizes like player so it needs to be slightly lower than for player
                product.setY(448);
                products.remove(i);
                i--; // Adjust the index to account for the removed item
                spawnProduct();
            }
        }
    }

    private void spawnProduct() {
        Random rnd = new Random();
        float x = rnd.nextInt(gg.getWidth() - 64);//generates random integer between 0 and the width of the game window minus 64 (the width of the product). the product spawns within the game window horizontally. (random x position)
        float y = 0;
        boolean isGood = rnd.nextBoolean(); //randomizing product type
        BufferedImage productImage = isGood ? goodProductImage : badProductImage; //he ternary operator ? :If isGood is true, productImage is set to goodProductImage.If isGood is false, productImage is set to badProductImage.it is short if
        int width = isGood ? 35 : 37;
        int height = isGood ? 36 : 30; //same thing here used just bcs the sizes of products
        products.add(new Products(x, y, width, height, isGood, isGood ? 0 : 1, productImage)); //here it indicates the damage if good, damage == 0 if bad, damage == 1
    }

    private void checkCollisions(){
        for (int i = 0; i < products.size(); i++){
            Products product = products.get(i);
            if(isCollided(player, product)){
                System.out.println("Collisioon");
                if (product.getProductDescription()){
                    score ++;
                }else{
                    player.decreaseLives();
                    score --;
                }
                products.remove(i);
                i--;
                spawnProduct();
            }
        }
    }

    private boolean isCollided(Player player, Products product){
        Rectangle playerBox = player.getCollisionBounds();
        Rectangle productBox = product.getCollisionBounds();
        return playerBox.intersects(productBox);
    }

    public int getScore (){
        return score;
    }
    private int getSpawnInterval(){ //calculates spawn interval based on game time
        int baseInterval = 2000;//milisecs
        int reduction = (int) (gameTime / 500); //reduces interval as game increases
        return Math.max(baseInterval - reduction, 500); //minimum interval 500 milis
    }
    private int getFallingSpeed (){
        int baseSpeed = 2;
        double multiplier = 0.5; //multiplier to slow down the increment
        int increase = (int) (gameTime / 2500); //adjust speed
        return baseSpeed + increase;
    }
}