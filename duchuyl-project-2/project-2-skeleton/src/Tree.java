import bagel.*;
import bagel.util.Rectangle;

/**
 * Represent a tree in level 1.
 */
public class Tree extends StationaryEntity{
    private final Image TREE = new Image("res/tree.png");
    public Tree(String x, String y) {
        this.x = Double.parseDouble(x);
        this.y = Double.parseDouble(y);
    }
    public void render() {
        TREE.drawFromTopLeft(x, y);
    }

    public int collide(Rectangle entity) {
        Rectangle TREEHitBox = new Rectangle(x, y,
                TREE.getWidth(), TREE.getHeight());
        if (TREEHitBox.intersects(entity)){
            return 1;
        }
        return 0;
    }
}
