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
    private int lastX;
    private int lastY;
    private int key;

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
        key = -1;
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

    public Dungeon getDungeon() {
        return dungeon;
    }
    public int getKey() {
        return key;
    }

    public void setKey(int pairCode) {
        this.key = pairCode;
    }

    /************************************************************
     *  Movement part                                           *
     ***********************************************************/
    public void moveUp() {
        if (dungeon.getGameStatus() != 0) {
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
        if (dungeon.getGameStatus() != 0) {
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
        if (dungeon.getGameStatus() != 0) {
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
        if (dungeon.getGameStatus() != 0) {
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
        if (this.potion == true) {
            return;
        } else if (this.weapon > 0) {
            this.weapon --;
        } else {
            dungeon.setGameStatus(-1);
        }
    }

    /************************************************************
     *  collectable tools part                                  *
     ***********************************************************/
    public void checkMoveToSquare() {
        // Entity removeEntity = null;
        //we need to make enemy move here.

        for (Entity e : dungeon.getEntities()) {
            if(e.getX() == getX() && e.getY() == getY()) {
                int handlePlayerResult = e.handlePlayer(this);
                if (handlePlayerResult == 1) {
                    break;
                } //else if (handlePlayerResult == 2) {
                //     removeEntity = e;
                //     break;
                // }//e.handlePlayer(this) would return 1 if e is not the user;
            }
        }
        dungeon.checkTreasureGoal();
        dungeon.checkEnemyGoal();
        // if (removeEntity != null) {
        //     dungeon.getEntities().remove(removeEntity);
        // }
    }
}
