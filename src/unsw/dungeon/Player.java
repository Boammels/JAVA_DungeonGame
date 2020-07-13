package unsw.dungeon;

/**
 * The player entity
 * @author Robert Clifton-Everest
 *
 */
public class Player extends Entity {

    private Dungeon dungeon;

    /**
     * Create a player positioned in square (x,y)
     * @param x
     * @param y
     */
    public Player(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.dungeon = dungeon;
    }

    // For each valid movement, we check if the player is on a wall, if it is, push it back.
    public void moveUp() {
        if (getY() > 0) {
            y().set(getY() - 1);
            if (isOnWall())
                y().set(getY() + 1);
        }
    }

    public void moveDown() {
        if (getY() < dungeon.getHeight() - 1) {
            y().set(getY() + 1);
            if (isOnWall())
                y().set(getY() - 1);
        }
    }

    public void moveLeft() {
        if (getX() > 0) {
            x().set(getX() - 1);
            if (isOnWall())
                x().set(getX() + 1);
        }
    }

    public void moveRight() {
        if (getX() < dungeon.getWidth() - 1) {
            x().set(getX() + 1);
            if (isOnWall())
                x().set(getX() - 1);
        }
    }

    private boolean isOnWall() {
        for (Entity e : dungeon.getEntities()) {
            // This is probably bad... hopefully we can make this class more dynamic
            // maybe not isOnWall, but isOnEntity, then it can be reused for weapons and other things.
            if (e instanceof Wall) {
                if (getX() == e.getX() && getY() == e.getY()) {
                    return true;
                }
            }
        }
        return false;
    }
}
