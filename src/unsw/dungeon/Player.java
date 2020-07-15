package unsw.dungeon;

/**
 * The player entity
 * @author Robert Clifton-Everest
 *
 */
public class Player extends Entity {

    private Dungeon dungeon;
    private boolean potion;
    private int weapon;

    /**
     * Create a player positioned in square (x,y)
     * @param x
     * @param y
     */
    public Player(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.dungeon = dungeon;
        potion = false;
        weapon = 0;
    }

    // For each valid movement, we check if the player is on a wall, if it is, push it back.
    public void moveUp() {
        if (getY() > 0) {
            y().set(getY() - 1);
            if (isOnWall())
                y().set(getY() + 1);
        }
        meetTools()
    }

    public void moveDown() {
        if (getY() < dungeon.getHeight() - 1) {
            y().set(getY() + 1);
            if (isOnWall())
                y().set(getY() - 1);
        }
        meetTools()
    }

    public void moveLeft() {
        if (getX() > 0) {
            x().set(getX() - 1);
            if (isOnWall())
                x().set(getX() + 1);
        }
        meetTools()
    }

    public void moveRight() {
        if (getX() < dungeon.getWidth() - 1) {
            x().set(getX() + 1);
            if (isOnWall())
                x().set(getX() - 1);
        }
        meetTools()
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

    public int getWeapon() {
        return this.weapon;
    }

    public void beAttacked() {
        if (this.weapon > 0) {
            this.weapon --;
        } else {
            //==TODO== end the game;
        }
    }

    public void meetTools() {
        for (Entity e :dungeon.getEntities()) {
            if(e.getX() == getX() && e.getY() == getY()) {
                if (e instanceof Weapon) {
                    pickupWeapon((Weapon)e);
                } else if (e instanceof Potion) {
                    pickupPotion((Potion)e);
                }
            }
        }
    }

    public boolean havePotion() {
        return this.potion;
    }

    public void pickupWeapon(Weapon weapon) {
        this.weapon += 5;
        weapon.pickedup();
    }

    public void pickupPotion(Potion potion) {
        this.potion = true;
        potion.pickedup();
    }
}
