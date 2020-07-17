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
    private int lastX, lastY;

    /**
     * Create a player positioned in square (x,y)
     * @param x
     * @param y
     */
    public Player(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.lastX = x;
        this.lastY = y;
        this.dungeon = dungeon;
        potion = false;
        weapon = 0;
    }

    public boolean havePotion() {
        return this.potion;
    }

    public void setPotion(boolean potion) {
        this.potion = potion;
    }

    public void setWeapon(int weapon) {
        this.weapon = weapon;
    }

    public int getLastX() {
        return lastX;
    }

    public int getLastY() {
        return lastY;
    }

    public void setLastX(int lastX) {
        this.lastX = lastX;
    }

    public void setLastY(int lastY) {
        this.lastY = lastY;
    }

    public boolean haveWeapon() {
        return this.weapon > 0;
    }

    public int getWeapon() {
        return this.weapon;
    }

    /************************************************************
     *  Movement part                                           *
     ***********************************************************/
    public void moveUp() {
        if (dungeon.getGameCompleted() != 0) {
            return;
        }
        lastX = getX();
        lastY = getY();
        if (getY() > 0) {
            setY(getY() - 1);
        }
        checkMoveToSquare();
    }

    public void moveDown() {
        if (dungeon.getGameCompleted() != 0) {
            return;
        }
        lastX = getX();
        lastY = getY();
        if (getY() < dungeon.getHeight() - 1) {
            setY(getY() + 1);
        }
        checkMoveToSquare();
    }

    public void moveLeft() {
        if (dungeon.getGameCompleted() != 0) {
            return;
        }
        lastX = getX();
        lastY = getY();
        if (getX() > 0) {
            setX(getX() - 1);
        }
        checkMoveToSquare();
    }

    public void moveRight() {
        if (dungeon.getGameCompleted() != 0) {
            return;
        }
        lastX = getX();
        lastY = getY();
        if (getX() < dungeon.getWidth() - 1) {
            setX(getX() + 1);
        }
        checkMoveToSquare();
    }

    @Override
    public int handlePlayer(Player p) {
        // This player will always be on the same coords as player so do nothing, alternatively
        // use a singular instanceof to make sure the entity is not a player
        return 0;
    }

    /************************************************************
     *  Enemy part                                              *
     ***********************************************************/

    public void beAttacked() {
        if (this.weapon > 0) {
            this.weapon --;
        } else {
            dungeon.endGame();
        }
    }

    /************************************************************
     *  collectable tools part                                  *
     ***********************************************************/
    public void checkMoveToSquare() {
        for (Entity e : dungeon.getEntities()) {
            if(e.getX() == getX() && e.getY() == getY()) {
                int a = e.handlePlayer(this);
                if (a == 1) {
                    break;
                }
            }
        }
    }
}
