package main;

import entity.PlayerValues;
import entity.Products;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class GameGraphics extends JFrame {
    public Draw draw;
    private int level;
    public GameGraphics(GameLogic logic, int level){
        this.level = level;
        this.draw = new Draw(logic, level);
        logic.setGameGraphics(this);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //Exit on close
        setResizable(false);
        add(draw);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setTitle("PRIBIGAME");
        addWindowFocusListener(new WindowFocusListener() { //if we lose focus on the window we are still moving so this will prevent the unnecessary movement
            @Override
            public void windowGainedFocus(WindowEvent e) { //if we are in window
            }

            @Override
            public void windowLostFocus(WindowEvent e) { //when we click somewhere else
                System.out.println("Byeee");
                logic.stopMoving();
            }
        });
    }

    public static class Draw extends JPanel{
        private final GameLogic logic;
        private BufferedImage[][] animations;
        private int aniTick, aniIndex, aniSpeed = 20;
        public int xMoving, yMoving;
        private BufferedImage playerImg, backroundImage;
        private BufferedImage goodProductImage, badProductImage;
        private BufferedImage healthBarImage;
        private int spriteAm = getSpriteAmount(PlayerValues.IDLE);
        private int level;

        public Draw (GameLogic logic, int level){
            this.level = level;
            this.logic = logic;
            xMoving = (int) logic.player.getX();
            yMoving = (int) logic.player.getY();//for the initial spawn point
            importBackgroundImg();
            importHealthBarImage();
            importPlayerImg();
            importProductImages();
            loadAni();
            setPanelSize();
        }

        public static int getSpriteAmount(PlayerValues values){
            /*
            TODO zmenit vsechny integery na pocet kolik je obrazku na jednom radku
             */
            switch (values){
                case RUNNING:
                    return 6;
                case IDLE:
                    return 5;
                case HIT:
                    return 4;
                case GOODPRODUCT:
                default:
                    return 1; //just workin for sure
            }
        }
        private void loadAni(){
            animations = new BufferedImage[9][6];       //length of animations arrays
            for (int j = 0; j < animations.length; j++) {
                for (int i = 0; i < animations[j].length; i++) {
                    animations[j][i] = playerImg.getSubimage(i * 64, j * 40, 64, 40);
                }
            }
        }
        private void importPlayerImg() {
            InputStream is = getClass().getResourceAsStream("/player_sprites.phootoshop.done.png");
            try {
                playerImg = ImageIO.read(is);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        public void importProductImages(){
            try {
                goodProductImage = ImageIO.read(getClass().getResourceAsStream("/pribinacek.png"));
                badProductImage = ImageIO.read(getClass().getResourceAsStream("/termix.png"));
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        public void importHealthBarImage(){
            InputStream is = getClass().getResourceAsStream("/hearth.photoshop.done.png");
            try {
                healthBarImage = ImageIO.read(is);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (is != null) {
                        is.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        private void importBackgroundImg(){
            String backgroundFile = switch (level) {
                case 2 -> "/backgroundLevelTwo.done.png";
                case 3 -> "/hellLevel.done.png";
                default -> "/backround.photoshop.done.png";
            };
            InputStream is = getClass().getResourceAsStream(backgroundFile);
            try {
                backroundImage = ImageIO.read(is);
            }catch (IOException e){
                e.printStackTrace();
            }finally {
                try {
                    is.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        private void setPanelSize() {
            Dimension size = new Dimension(600, 750);
            setPreferredSize(size);   //for JPanel to fit in JFrame
        }

        private void updateAniTick() {
            aniTick ++;
            if (aniTick >= aniSpeed){
                aniTick = 0;
                aniIndex++;
                if (aniIndex >= spriteAm){   //resets animation index
                    aniIndex = 0;
                }
            }
        }

        protected void paintComponent(Graphics g){
            super.paintComponent(g);
            updateAniTick();
            //draw Background
            g.drawImage(backroundImage, 0,0, this.getWidth(), this.getHeight(), null);

            //draw HealthBar
            int healthBarWidth = 32 * logic.player.getLives();  // 3 hearts * 32 pixels each
            int healthBarHeight = 32; // height of one heart
            int healthBarX = this.getWidth() - (healthBarWidth + 20); // 20 pixels padding from right
            int healthBarY = 20; // 20 pixels padding from top
            for (int i = 0; i < logic.player.getLives(); i++){
                g.drawImage(healthBarImage, healthBarX + i * 32, healthBarY, 32, 32,null);
            }

            //draw Score
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("Score: " + logic.getScore(), 225, 650);

            //draw Player
            PlayerValues currentAction = logic.player.getAction();
            spriteAm = getSpriteAmount(currentAction);
            g.drawImage(animations[currentAction.ordinal()][aniIndex], xMoving, yMoving, 128, 80, null);// players size || The ordinal() method in Java is used to get the ordinal value (the position) of an enum constant. Each enum constant has an ordinal value that represents its position in the enum declaration, starting from zero.

            //draw Product
            for(Products product: logic.products){
                g.drawImage(product.getImage(), (int) product.getX(), (int) product.getY(), product.getWidth(), product.getHeight(), null);
            }
        }

        public BufferedImage getBadProductImage() {
            return badProductImage;
        }

        public BufferedImage getGoodProductImage() {
            return goodProductImage;
        }
    }
}