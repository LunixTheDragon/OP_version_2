package entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Products extends Entity {
    private int damageIfBad = 1;
    private boolean productDescription; //we do not need to write = true bcs now is
    private BufferedImage image;

    public Products(float x, float y, int width, int height, boolean productDescription, int damageIfBad, BufferedImage image) {
        super(x, y, width, height);
        this.productDescription = productDescription;
        this.damageIfBad = damageIfBad;
        this.image = image;
    }

    public int getPosition(){
        return (int) this.y;
    }

    public int getDamageIfBad() {
        return damageIfBad;
    }

    public void setDamageIfBad(int damageIfBad) {
        this.damageIfBad = damageIfBad;
    }

    public boolean getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(boolean productDescription) {
        this.productDescription = productDescription;
    }
    public void setImage (BufferedImage image){
        this.image = image;
    }
    public BufferedImage getImage(){
        return image;
    }
}