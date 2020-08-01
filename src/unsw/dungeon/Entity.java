package unsw.dungeon;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
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
    private BooleanProperty show;
    private int initX;
    private int initY;

    /**
     * Create an entity positioned in square (x,y)
     * @param x
     * @param y
     */
    public Entity(int x, int y) {
        this.x = new SimpleIntegerProperty(x);
        this.y = new SimpleIntegerProperty(y);
        this.initX = x;
        this.initY = y;
        BooleanProperty toShow = new SimpleBooleanProperty();
        toShow.set(true);
        this.show = toShow;
    }

    public IntegerProperty x() {
        return x;
    }

    public IntegerProperty y() {
        return y;
    }

    public BooleanProperty getShow() {
        return show;
    }

    public void setShow(Boolean value) {
        this.getShow().set(value);
    }

    public int getY() {
        return y().get();
    }

    public int getX() {
        return x().get();
    }

    public int getInitX() {
        return initX;
    }

    public int getInitY() {
        return initY;
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
    }
    
    /**
     * Used by enemies to determine whether they can walk over this square
     * @return
     */
    public boolean isWall() {
        return false;
    }

	public void teleportEnemy(Enemy enemy) {
    }
    
    public void teleportBoulder(Boulder boulder) {
    }

    public void cancelTimer() {
    }
}
