import bagel.*;
import bagel.util.Rectangle;

/**
 * Represent Navec in level 1.
 * Navec is the boss of level 1.
 * Level 1 is completed when Navec is dead.
 */
public class Navec extends Demon{
    private final Image NAVEC_LEFT = new Image("res/navec/navecLeft.png");
    private final Image NAVEC_RIGHT = new Image("res/navec/navecRight.png");
    private final Image NAVEC_INVINCIBLE_LEFT = new Image("res/navec/navecInvincibleLeft.png");
    private final Image NAVEC_INVINCIBLE_RIGHT = new Image("res/navec/navecInvincibleRight.png");
    private final Image NAVEC_FIRE = new Image("res/navec/navecFire.png");

    public Navec(String a, String b){
        super(a, b);
        int BOSS_POWER_MULTIPLIER = 2;
        this.health *= BOSS_POWER_MULTIPLIER;
        this.maxHealth *= BOSS_POWER_MULTIPLIER;
        this.healthPercentage = health * 100 / maxHealth;
        this.isAggressive = 1;
        this.damage *= BOSS_POWER_MULTIPLIER;
    }

    /**
     * Performs rendering Navec in normal state and invincible state.
     */
    public void render(){
        Image left, right;
        if (isInvincible == 1){
            left = NAVEC_INVINCIBLE_LEFT;
            right = NAVEC_INVINCIBLE_RIGHT;
            if (invincibleFrame == INVINCIBLE_MAX_FRAME){
                invincibleFrame = 0;
                isInvincible = 0;
            } else {
                invincibleFrame++;
            }
        } else {
            left = NAVEC_LEFT;
            right = NAVEC_RIGHT;
        }
        switch (direction){
            case 0:
                x -= speed;
                left.drawFromTopLeft(x, y);
                break;
            case 1:
                y -= speed;
                left.drawFromTopLeft(x, y);
                break;
            case 2:
                x += speed;
                right.drawFromTopLeft(x, y);
                break;
            case 3:
                y += speed;
                right.drawFromTopLeft(x, y);
                break;
        }
    }

    /**
     * Performs rendering navec's attack
     */
    public int renderFire(double playerCenterX, double playerCenterY, Rectangle player){
        int attackDirection = fireDirection(playerCenterX, playerCenterY);
        return fireImplementation(NAVEC_LEFT, NAVEC_RIGHT, NAVEC_FIRE, attackDirection, player);
    }

    /**
     * Check if level 1 is completed.
     */
    public boolean level1Complete(){
        return health <= 0;
    }

    /**
     * Print log to stdout if Navec take damage from player.
     */
    protected void printLog(){
        System.out.println("Fae inflicts 20 damage points on Navec. " +
                "Navec’s current health: " + df.format(health) + "/" + df.format(maxHealth));
    }

    /**
     * Print log to stdout if Nave inflicts damage to player.
     */
    protected void printLogDamagePlayer(Player player){
        System.out.println("Navec inflicts 20 damage points on Fae. " +
                "Fae’s current health: " + df.format(player.getHealth()) +
                "/" + df.format(player.getMaxHealth()));
    }

}
