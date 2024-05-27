package windows;

import main.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JFrame {
    private JButton startButton;
    private JButton levelTwoButton;
    private JButton hardcoreLevelButton;
    private JButton helpButton;
    public Menu (){
        setTitle("Game Menu");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        BackgroundMenuPanel backgroundMenuPanel = new BackgroundMenuPanel("/pozadi.napad.png");
        backgroundMenuPanel.setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();

        startButton = new JButton("Level 1");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game game = new Game(1);
                game.startGame();
                dispose();
            }
        });
        levelTwoButton = new JButton("Level 2");
        levelTwoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game game= new Game(2);
                game.startGame();
                dispose();
            }
        });

        hardcoreLevelButton = new JButton("Level HARDCORE");
        hardcoreLevelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game game = new Game(3);
                game.startGame();
                dispose();
            }
        });

        helpButton = new JButton("Help");
        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new HelpPanel().setVisible(true);
                dispose();
            }
        });

        g.gridx = 0;
        g.gridy = 0;
        g.insets = new Insets(10, 10, 10, 10);
        g.fill = GridBagConstraints.NONE;
        backgroundMenuPanel.add(startButton, g);

        g.gridy = 1;
        backgroundMenuPanel.add(levelTwoButton, g);

        g.gridy = 2;
        backgroundMenuPanel.add(hardcoreLevelButton, g);

        g.gridy = 3;
        backgroundMenuPanel.add(helpButton, g);

        add(backgroundMenuPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.setVisible(true);
    }
}
