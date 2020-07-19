package unsw.dungeon;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * An entity in the dungeon.
 * @author Robert Clifton-Everest
 *
 */
public class Entity {

    // IntegerProperty is used so that changes to the entities position can be
    // externally observed.
    private IntegerProperty x;
    private IntegerProperty y;

    /**
     * Create an entity positioned in square (x,y)
     * @param x
     * @param y
     */
    public Entity(int x, int y) {
        this.x = new SimpleIntegerProperty(x);
        this.y = new SimpleIntegerProperty(y);
    }

    public IntegerProperty x() {
        return x;
    }

    public IntegerProperty y() {
        return y;
    }

    public int getY() {
        return y().get();
    }

    public int getX() {
        return x().get();
    }

    public void setY(int newY) {
        this.y().set(newY);
    }

    public void setX(int newX) {
        this.x().set(newX);
    }
    
    /**
     * When a player is on an entity, handle what happens
     * OVERWRITTEN by each respective entity
     * @param p - player object
     * @return 1 - if we remove this object, so we can break out of the loop straight away | 0 otherwise
     */
    public int handlePlayer(Player p) {
        throw new java.lang.UnsupportedOperationException("Handle player not yet written for: " + this.getClass());
    }

	public void addPortal(Portal portal) {
        return;
    }
    
    /**
     * Used by enemies to determine whether they can walk over this square
     * @return
     */
    public boolean isWall() {
        return false;
    }
}
