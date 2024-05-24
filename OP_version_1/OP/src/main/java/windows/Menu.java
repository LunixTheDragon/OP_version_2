package windows;

import main.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JFrame {
    private JButton startButton;
    public Menu (){
        setTitle("Game Menu");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        BackgroundMenuPanel bacroundMenuPanel = new BackgroundMenuPanel("/pozadi.napad.png");
        bacroundMenuPanel.setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();

        startButton = new JButton("Start Game");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game game = new Game();
                game.startGame();
                dispose();
            }
        });

        g.gridx = 0;
        g.gridy = 0;
        g.insets = new Insets(10, 10, 10, 10);
        g.fill = GridBagConstraints.NONE;
        bacroundMenuPanel.add(startButton, g);
        add(bacroundMenuPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.setVisible(true);
    }
}
