package main;

import entity.PlayerValues;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game implements Runnable{
    GameLogic logic;
    GameGraphics gg;
    private Thread gameThread;
    private final int FPS = 120;
    public Game(){
        logic = new GameLogic();
        logic.initialize(); //GameLogic must be iniciliazed before GameGraphics for player to not to be null
        gg = new GameGraphics(logic);
        logic.setGameGraphics(gg);
        logic.spawnInitialProducts();

        gg.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()){
                    case KeyEvent.VK_D, KeyEvent.VK_A, KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT:
                        logic.setDirection(e.getKeyCode());
                        logic.setMoving(true);
                        break;
                    default:
                        logic.updatePlayerAction(PlayerValues.IDLE);
                        logic.setMoving(false);
                        break;
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {
                logic.setMoving(false);
                logic.updatePlayerAction(PlayerValues.IDLE);
            }
        });
        gg.setFocusable(true);
    }
    public void startGame(){
        gameThread = new Thread(this);
        gameThread.start();
    }
    @Override
    public void run() {
        double timePerFrame = 1000000000.0 / FPS;
        long lastFrame = System.nanoTime();
        long now ;
        int frames = 0;
        long lastCheck = System.currentTimeMillis();

        while (true) {
            now = System.nanoTime();
            if (now - lastFrame >= timePerFrame) {
                logic.update();
                gg.draw.repaint();
                lastFrame = now;
                frames++;
            }
            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.startGame();
    }
}