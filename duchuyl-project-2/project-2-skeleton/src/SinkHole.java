import bagel.*;
import bagel.util.Rectangle;

/**
 * Represent sinkhole in the game
 * Sinkhole can interact with entity
 */
public class SinkHole extends StationaryEntity{
    private final Image SINK_HOLE = new Image("res/sinkhole.png");
    /**
     * Used to check if the sinkhole is step on or not
     */
    public int isStepOn;
    public final int damage = 30;

    public SinkHole(String x, String y) {
        this.x = Double.parseDouble(x);
        this.y = Double.parseDouble(y);
        isStepOn = 0;
    }

    public int getIsStepOn() {
        return isStepOn;
    }

    public int getDamage() {
        return damage;
    }

    public void setIsStepOn(int isStepOn) {
        this.isStepOn = isStepOn;
    }

    @Override
    public int collide(Rectangle player) {
        Rectangle sinkHoleHitBox = new Rectangle(x, y, SINK_HOLE.getWidth(), SINK_HOLE.getHeight());
        if (sinkHoleHitBox.intersects(player) && isStepOn == 0){
            isStepOn = 1;
            return 1;
        }
        return 0;
    }

    /**
     * Used to check if demon collide with sinkhole
     */
    public int collideDemon(Rectangle demon) {
        Rectangle sinkHoleHitBox = new Rectangle(x, y, SINK_HOLE.getWidth(), SINK_HOLE.getHeight());
        if (sinkHoleHitBox.intersects(demon) && isStepOn == 0){
            return 1;
        }
        return 0;
    }
    @Override
    public void render() {
        SINK_HOLE.drawFromTopLeft(x, y);
    }
}

