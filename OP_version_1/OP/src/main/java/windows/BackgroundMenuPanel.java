package windows;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class BackgroundMenuPanel extends JPanel {
    private BufferedImage backgroundMenuImage;
    public BackgroundMenuPanel(String fileName){
        try {
            backgroundMenuImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(fileName)));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        if (backgroundMenuImage != null){
            g.drawImage(backgroundMenuImage, 0, 0, this.getWidth(), this.getHeight(), this);
        }
    }
}
