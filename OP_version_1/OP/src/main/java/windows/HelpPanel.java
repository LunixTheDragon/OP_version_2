package windows;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class HelpPanel extends JFrame {
    private BufferedImage backgroundImage;
    public HelpPanel(){
        setTitle("HelpMenu");
        setSize(600, 750);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        try {
            backgroundImage = ImageIO.read(getClass().getResource("/helpPanel.done.png"));
        }catch (IOException e){
            e.printStackTrace();
        }

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

        JButton returnButton = new JButton("Return");
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Menu().setVisible(true);
                dispose();
            }
        });

        g.gridx = 0;
        g.gridy = 1;
        g.insets = new Insets(20, 20, 20, 20);
        g.anchor = GridBagConstraints.SOUTH;
        panel.add(returnButton, g);

        add(panel, BorderLayout.CENTER);

    }
}
