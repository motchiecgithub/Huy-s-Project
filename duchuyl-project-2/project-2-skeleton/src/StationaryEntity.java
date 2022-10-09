import bagel.util.Rectangle;

/**
 * Represent all stationary object in the game
 * Stationary object can interact with entity
 */
public abstract class StationaryEntity {
    /**
     * coordinate x and y of object
     */
    protected double x, y;

    /**
     * Performs collide implementation
     * @return if player collide with stationary entity
     */
    public abstract int collide(Rectangle entity);

    /**
     * Performs rendering stationary entity
     */
    public abstract void render();
}
