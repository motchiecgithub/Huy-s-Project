import bagel.*;
import bagel.util.Rectangle;

/**
 * Represent a wall in level 0.
 */
public class Wall extends StationaryEntity{
    private final Image WALL = new Image("res/wall.png");

    public Wall(String x, String y) {
        this.x = Double.parseDouble(x);
        this.y = Double.parseDouble(y);
    }
    @Override
    public void render() {
        WALL.drawFromTopLeft(x, y);
    }

    @Override
    public int collide(Rectangle entity) {
        Rectangle wallHitBox = new Rectangle(x, y,
                WALL.getWidth(), WALL.getHeight());
        if (wallHitBox.intersects(entity)){
            return 1;
        }
        return 0;
    }
}
