import bagel.*;
import bagel.util.Rectangle;
import java.text.DecimalFormat;

/**
 * Represent player in the game
 * player can interact with all other entity
 */
public class Player extends Entity{
    private final Image FAE_LEFT = new Image("res/fae/faeLeft.png");
    private final Image FAE_RIGHT = new Image("res/fae/faeRight.png");
    private final Image FAE_ATTACK_LEFT = new Image("res/fae/faeAttackLeft.png");
    private final Image FAE_ATTACK_RIGHT = new Image("res/fae/faeAttackRight.png");
    private final Font HEALTH_FONT = new Font("res/frostbite.ttf", 30);
    /**
     * Used to check if player facing left
     */
    private int isFacingLeft;
    private final int DAMAGE = 20;
    public void setIsFacingLeft(int isFacingLeft) {
        this.isFacingLeft = isFacingLeft;
    }

    public int getDAMAGE() {
        return DAMAGE;
    }

    public Player(String x, String y){
        this.x = Double.parseDouble(x);
        this.y = Double.parseDouble(y);
        this.isFacingLeft = 0;
        this.maxHealth = 100;
        this.health = maxHealth;
        this.healthPercentage = 100;
    }

    /**
     * Represent border
     */
    private double top, left, bottom, right;
    public double getTop() {
        return top;
    }

    public double getLeft() {
        return left;
    }

    public double getBottom() {
        return bottom;
    }

    public double getRight() {
        return right;
    }

    /**
     * set top left of the border
     */
    public void setTopLeft(String left, String top){
        this.top = Double.parseDouble(top);
        this.left = Double.parseDouble(left);
    }

    /**
     * set bottom right of the border
     */
    public void setBottomRight(String right, String bottom){
        this.bottom = Double.parseDouble(bottom);
        this.right = Double.parseDouble(right);
    }

    /**
     * Check if player cross the border
     */
    public boolean borderCheck(){
        return y < top || y > bottom
                || x < left || x > right;
    }

    /**
     * Print log to stdout when player step on sink hole
     */
    public void printLog(){
        System.out.println("Sinkhole inflicts 30 damage points on Fae. Fae’s current health: " +
                df.format(health) + "/" + df.format(maxHealth));
    }

    /**
     * Performs rendering player in idle state.
     */
    public void render(){
        if (isFacingLeft == 0){
            FAE_RIGHT.drawFromTopLeft(x, y);
        } else {
            FAE_LEFT.drawFromTopLeft(x, y);
        }
    }

    /**
     * Performs rendering player in attack state.
     */
    public void renderAttack(){
        if (isFacingLeft == 0){
            FAE_ATTACK_RIGHT.drawFromTopLeft(x, y);
        } else {
            FAE_ATTACK_LEFT.drawFromTopLeft(x - 6, y);
        }
    }

    /**
     * Create rectangle player to check collide with other entity.
     */
    public Rectangle createRectangle(){
        return new Rectangle(x, y, FAE_LEFT.getWidth(), FAE_LEFT.getHeight());
    }

    /**
     * Update movement of player.
     * @param x horizontal movement.
     * @param y vertical movement.
     */
    public void updateMove(double x, double y){
        this.x += x;
        this.y += y;
    }

    /**
     * Used to check when user is lost.
     */
    public boolean loseCheck(){
        return health == 0;
    }

    /**
     * Used to check when user complete level 0.
     */
    public boolean level0WinCheck(){
        return this.x >= 950 && this.y >= 670;
    }

    /**
     * Draw player health bar.
     */
    public void drawPlayerHealth(){
        HEALTH_FONT.drawString(df.format(healthPercentage) + "%",
                20, 25, getColor(healthPercentage));
    }

    /**
     * Get player center coordinate.
     */
    public double getPlayerCenterX(){
        return x + FAE_LEFT.getWidth() / 2.0;
    }
    public double getPlayerCenterY(){
        return y + FAE_LEFT.getHeight() / 2.0;
    }

    /**
     * Performs transition to next level.
     * @param coordinate coordinate of player in next level.
     * @param topLeft coordinate top left of border in next level.
     * @param bottomRight coordinate bottom right of border in next level.
     */
    public void transition(Point coordinate, Point topLeft, Point bottomRight){
        this.x = coordinate.getX();
        this.y = coordinate.getY();
        this.left = topLeft.getX();
        this.right = bottomRight.getX();
        this.top = topLeft.getY();
        this.bottom = bottomRight.getY();
        this.isFacingLeft = 0;
        this.maxHealth = 100;
        this.health = maxHealth;
        this.healthPercentage = 100;
    }
}
