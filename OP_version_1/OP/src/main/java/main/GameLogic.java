package main;

import entity.Backround;
import entity.Player;
import entity.PlayerValues;
import entity.Products;

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
    private BufferedImage[] productImages;

    public void initialize() {
        player = new Player(230, 441, 64, 40, 3); //where is spawn player, y = 441 exact position of ground
        products = new ArrayList<>();
        backround = new Backround(0, 0, 600, 750);
    }

    public void setGameGraphics(GameGraphics gg) {
        this.gg = gg;
        this.productImages = gg.draw.getProductImages();
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

    private boolean descriptionOfProduct() {
        Random rnd = new Random();
        return rnd.nextBoolean();
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
                    gg.draw.xDelta -= 3; //Players speed
                    break;
                case KeyEvent.VK_D, KeyEvent.VK_RIGHT:
                    gg.draw.xDelta += 3;
                    break;
            }
        }
    }

    public void update() {
        setAni();
        updatePos();
        updateProducts();
        if (products.isEmpty()){
            spawnProduct(); //ensures one product is spawned at a time
        }
    }

    private void updateProducts() {
        for (int i = 0; i < products.size(); i++) {
            Products product = products.get(i);
            product.setY(product.getY() + 2); //falling speed
            if (product.getPosition() >= 448) {   // they dont have exact right sizes like player so it needs to be slightly lower than for player
                product.setY(448);
                products.remove(i);
                i--; // Adjust the index to account for the removed item
            }
        }
    }

    private void spawnProduct() {
        Random rnd = new Random();
        float x = rnd.nextInt(gg.getWidth() - 64);//width of product dont forget to change it in GameGraphics
        float y = 0;
        boolean isGood = descriptionOfProduct();
        BufferedImage productImage = productImages[rnd.nextInt(productImages.length)]; // randomly select productImage
        products.add(new Products(x, y, 64, 80, isGood, isGood ? 0 : 1, productImage));
    }
}