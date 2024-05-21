package entity;

import java.util.Random;
public class Products extends Entity {
    private int damageIfBad = 1;
    private boolean productDescription = true;
    private final float speed = 2.0f; // Speed at which the product falls, why should we use float? firstly 2.0 is double literal instead 2.0f is a float literal and its more  beneficial bcs float occupies 4 bytes, and double 8 bytes

    public Products(float x, float y, int width, int height, boolean productDescription, int damageIfBad) {
        super(x, y, width, height);
        this.productDescription = productDescription;
        this.damageIfBad = damageIfBad;
    }

    public int getPosition(){
        return (int)this.y;
    }
    public int getDamageIfBad() {
        return damageIfBad;
    }

    public void setDamageIfBad(int damageIfBad) {
        this.damageIfBad = damageIfBad;
    }

    public boolean isProductDescription() {
        return productDescription;
    }

    public void setProductDescription(boolean productDescription) {
        this.productDescription = productDescription;
    }
}
