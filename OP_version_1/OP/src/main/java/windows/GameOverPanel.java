package windows;

import main.Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GameOverPanel extends JFrame {
    private BufferedImage backgroundImage;
    public GameOverPanel(){
        setTitle("Game Over");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        try {
            backgroundImage = ImageIO.read(getClass().getResource("/deathScreen.png"));
        }catch (IOException e){
            e.printStackTrace();
        }

        // Panel for components
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        panel.setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();

        //gm text
        JLabel gameOverText = new JLabel("GAME OVER");
        gameOverText.setFont(new Font("Arial", Font.BOLD, 48));
        gameOverText.setForeground(Color.RED);
        g.gridx = 0;
        g.gridy = 0;
        g.gridwidth = 2;
        g.insets = new Insets(20, 20, 20, 20);
        panel.add(gameOverText, g);

        JButton restartButton = new JButton("Restart");
        g.gridy = 1;
        g.gridwidth = 1;
        g.insets = new Insets(10, 20, 10, 20);
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Game().startGame();
                dispose();
            }
        });
        panel.add(restartButton, g);

        JButton menuButton = new JButton("Menu");
        g.gridx = 1;
        g.insets = new Insets(10, 40, 10, 20); //space between this and another component
        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Menu().setVisible(true);
                dispose();
            }
        });
        panel.add(menuButton, g);
        add(panel, BorderLayout.CENTER);
    }
}