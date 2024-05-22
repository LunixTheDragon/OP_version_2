package entity;

public class Products extends Entity {
    private int damageIfBad = 1;
    private boolean productDescription = true;

    public Products(float x, float y, int width, int height, boolean productDescription, int damageIfBad) {
        super(x, y, width, height);
        this.productDescription = productDescription;
        this.damageIfBad = damageIfBad;
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

    public boolean isProductDescription() {
        return productDescription;
    }

    public void setProductDescription(boolean productDescription) {
        this.productDescription = productDescription;
    }
}