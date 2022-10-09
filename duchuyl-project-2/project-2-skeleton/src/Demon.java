import bagel.*;
import bagel.util.Rectangle;

import java.util.Random;
import java.lang.Math;

/**
 * Represent a demon in level 1.
 * A demon can move and attack player
 */
public class Demon extends Entity{
    private final Image DEMON_LEFT = new Image("res/demon/demonLeft.png");
    private final Image DEMON_RIGHT = new Image("res/demon/demonRight.png");
    private final Image DEMON_INVINCIBLE_LEFT = new Image("res/demon/demonInvincibleLeft.png");
    private final Image DEMON_INVINCIBLE_RIGHT = new Image("res/demon/demonInvincibleRight.png");
    private final Image DEMON_FIRE = new Image("res/demon/demonFire.png");
    /**
     * Used to generate demon speed.
     */
    Random random = new Random();
    /**
     * Used to check if demon is aggressive.
     */
    protected int isAggressive;
    /**
     * Used to check if demon in invincible state.
     */
    protected int isInvincible;
    /**
     * Used to count of frame of invincible state.
     */
    protected int invincibleFrame = 0;
    /**
     * Max frame of invincible state.
     */
    protected final int INVINCIBLE_MAX_FRAME = 179;
    /**
     * Damage to player.
     */
    protected int damage = 10;

    public int getDamage() {
        return damage;
    }

    /**
     * Used to rotate the Fire image.
     */
    protected DrawOptions fireRenderDirection = new DrawOptions();

    public Demon(String x, String y){
        this.x = Double.parseDouble(x);
        this.y = Double.parseDouble(y);
        this.health = 40;
        this.healthPercentage = 100;
        this.maxHealth = 40;
        this.isAggressive = random.nextInt(2);
        this.isInvincible = 0;
    }

    /**
     * Generate random demon speed between 0.2 and 0.7.
     */
    protected double speed = (random.nextInt(6) + 2.0) / 10.0;
    /**
     * Generate random demon moving direction.
     */
    protected int direction = random.nextInt(4);

    /**
     * Change movement direction when collide with object.
     */
    public void changeDirection() {
        direction = (direction + 2) % 4;
    }

    /**
     * Method used to render demon in idle and invincible state.
     */
    public void render(){
        Image left, right;
        if (isInvincible == 1){
            left = DEMON_INVINCIBLE_LEFT;
            right = DEMON_INVINCIBLE_RIGHT;
            if (invincibleFrame == INVINCIBLE_MAX_FRAME){
                invincibleFrame = 0;
                isInvincible = 0;
            } else {
                invincibleFrame++;
            }
        } else {
            left = DEMON_LEFT;
            right = DEMON_RIGHT;
        }
        if (health > 0){
            if (isAggressive == 1){
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
            } else {
                switch (direction){
                    case 0:
                    case 2:
                        left.drawFromTopLeft(x, y);
                        break;
                    case 1:
                    case 3:
                        right.drawFromTopLeft(x, y);
                        break;
                }
            }
        }
    }

    /**
     * Used to check if demon cross the border.
     */
    public boolean borderCheck(double top, double bottom, double left, double right){
        return y < top || y > bottom
                || x < left || x > right;
    }

    /**
     * Create rectangle of demon to check collide with other entity.
     */
    public Rectangle createRectangle(){
        return new Rectangle(x, y, DEMON_LEFT.getWidth(), DEMON_LEFT.getHeight());
    }

    /**
     * Calculate fire direction.
     */
    protected int fireDirection(double playerCenterX, double playerCenterY){
        /* return direction of demon attack */
        double centerX = x + DEMON_LEFT.getWidth() / 2.0;
        double centerY = y + DEMON_LEFT.getHeight() / 2.0;
        if (playerCenterX <= centerX && playerCenterY <= centerY){
            return 0;
        }
        if (playerCenterX <= centerX) {
            return 1;
        }
        if (playerCenterY <= centerY){
            return 2;
        }
        return 3;
    }

    /**
     * Check if player in demon attack range.
     */
    public int inAttackRange(double playerCenterX, double playerCenterY){
        double centerX = x + DEMON_LEFT.getWidth() / 2.0;
        double centerY = y + DEMON_LEFT.getHeight() / 2.0;
        double distance = Math.sqrt(Math.pow((playerCenterX - centerX), 2) +
                Math.pow((playerCenterY - centerY), 2));
        if (distance <= 150) return 1;
        return 0;
    }

    /**
     * Performs fire implementation.
     * @param attackDirection direction to attack player.
     * @return if player collide with demon attack.
     */
    protected int fireImplementation(Image imageLeft, Image imageRight,
                    Image imageFire, int attackDirection, Rectangle player){
        switch (attackDirection){
            case 0:
                fireRenderDirection.setRotation(Math.PI);
                imageFire.drawFromTopLeft(x - imageFire.getWidth(),
                        y - imageFire.getHeight());
                Rectangle fire = new Rectangle(x - imageFire.getWidth(),
                        y - imageFire.getHeight(), imageFire.getWidth(), imageFire.getHeight());
                if (fire.intersects(player)) return 1;
                break;
            case 1:
                fireRenderDirection.setRotation(3.0 * Math.PI / 2.0);
                imageFire.drawFromTopLeft(x - imageFire.getWidth(),
                        y + imageLeft.getHeight(), fireRenderDirection);
                Rectangle fire2 = new Rectangle(x - imageFire.getWidth(),
                        y + imageLeft.getHeight(), imageFire.getWidth(), imageFire.getHeight());
                if (fire2.intersects(player)) return 1;
                break;
            case 2:
                fireRenderDirection.setRotation(Math.PI / 2.0);
                imageFire.drawFromTopLeft(x + imageLeft.getWidth(),
                        y - imageFire.getHeight(), fireRenderDirection);
                Rectangle fire3 = new Rectangle(x + imageLeft.getWidth(),
                        y - imageFire.getHeight(), imageFire.getWidth(), imageFire.getHeight());
                if (fire3.intersects(player)) return 1;
                break;
            case 3:
                fireRenderDirection.setRotation(Math.PI);
                imageFire.drawFromTopLeft(x + imageLeft.getWidth(),
                        y + imageLeft.getHeight(), fireRenderDirection);
                Rectangle fire4 = new Rectangle(x + imageLeft.getWidth(),
                        y + imageLeft.getHeight(), imageLeft.getWidth(), imageFire.getHeight());
                if (fire4.intersects(player)) return 1;
                break;
        }
        return 0;
    }

    /**
     * Perform demon attack state.
     */
    public int renderFire(double playerCenterX, double playerCenterY, Rectangle player){
        int attackDirection = fireDirection(playerCenterX, playerCenterY);
        return fireImplementation(DEMON_LEFT, DEMON_RIGHT, DEMON_FIRE, attackDirection, player);
    }


    /**
     * Performs demon taking damage from player.
     */
    protected void takeDamaged(Rectangle player, int playerDamage){
        Rectangle demon = createRectangle();
        if (demon.intersects(player) && isInvincible == 0){
            health -= playerDamage;
            healthPercentage = health * 100 / maxHealth;
            isInvincible = 1;
        }
    }

    /**
     * Print log to stdout when taking damage from player.
     */
    protected void printLog(){
        System.out.println("Fae inflicts 20 damage points on Demon. " +
                "Demon’s current health: " + df.format(health) + "/" + df.format(maxHealth));
    }

    /**
     * Print log to stdout if demon inflicts damage to player.
     */
    protected void printLogDamagePlayer(Player player){
        System.out.println("Demon inflicts 20 damage points on Fae. " +
                "Fae’s current health: " + df.format(player.getHealth())
                + "/" + df.format(player.getMaxHealth()));
    }

    /**
     * Performs changing demon speed.
     */
    protected void changeTimeScale(int timeScale){
        if (timeScale == 1){
            speed = speed + 0.5 * speed;
        } else if (timeScale == -1) {
            speed = speed - 0.5 * speed;
        }
    }

}
