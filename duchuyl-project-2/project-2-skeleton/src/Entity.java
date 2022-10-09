import bagel.*;
import java.text.DecimalFormat;

public class Entity{
    /**
     * Represent coordinate of entity
     */
    protected double x = 0;
    protected double y = 0;
    /**
     * Represent health of entity
     */
    protected double health;
    protected double healthPercentage;
    protected double maxHealth;

    /**
     * Used to change the color of health bar
     */
    private final DrawOptions color = new DrawOptions();
    /**
     * Used to print in zero decimal place.
     */
    protected final DecimalFormat df = new DecimalFormat("0");
    /**
     * Used to render player health.
     */
    private final Font HEALTH_FONT = new Font("res/frostbite.ttf", 15);

    /**
     * Used to change the color of health bar.
     */
    protected final DrawOptions getColor(double healthPercentage) {
        if (healthPercentage > 65){
            color.setBlendColour(0, 0.8, 0.2);
        } else if (healthPercentage > 35){
            color.setBlendColour(0.9, 0.6, 0);
        } else {
            color.setBlendColour(1,0,0);
        }
        return color;
    }

    /**
     * Draw health bar.
     */
    public void drawHealth(double healthPercentage){
        HEALTH_FONT.drawString(df.format(healthPercentage) + "%",
                x, y - 6, getColor(healthPercentage));
    }
    public double getHeathPercentage(){
        return healthPercentage;
    }

    public double getHealth() {
        return health;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    /**
     * Used to calculate entity health when getting damaged
     */
    public void getDamaged (int damage){
        health -= damage;
        healthPercentage = health;
        if (health < 0){
            health = 0;
            healthPercentage = 0;
        }
    }

}