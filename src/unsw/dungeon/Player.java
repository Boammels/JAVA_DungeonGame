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
    private Weapon weaponObject;
    private int lastX;
    private int lastY;
    private int key;
    private Key keyObject;

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

    /**
     * Gets if player has potion
     * @return boolean potion
     */
    public boolean havePotion() {
        return this.potion;
    }

    /**
     * Sets whether player has a potion
     * @param potion
     */
    public void setPotion(boolean potion) {
        this.potion = potion;
    }

    /**
     * Set the hit points for the players weapon
     * @param weapon
     */
    public void setWeapon(int weapon) {
        this.weapon = weapon;
    }

    public void setWeaponObject(Weapon w) {
        this.weaponObject = w;
    }

    public Weapon getWeaponObject() {
        return weaponObject;
    }

    /**
     * Get the last X coordinate the player has been on (not the current)
     * @return
     */
    public int getLastX() {
        return lastX;
    }

    /**
     * Get the last Y coordinate the player has been on (not the current)
     * @return
     */
    public int getLastY() {
        return lastY;
    }

    /**
     * Set the last X coordinate the player has been on
     * @param lastX
     */
    public void setLastX(int lastX) {
        this.lastX = lastX;
    }

    /**
     * Set the last Y coordinate the player has been on
     * @param lastY
     */
    public void setLastY(int lastY) {
        this.lastY = lastY;
    }

    /**
     * Get whether or not the player is currently golding a weapon
     * @return
     */
    public boolean haveWeapon() {
        return this.weapon > 0;
    }

    /**
     * Returns how many hit points the weapon currently has
     * @return
     */
    public int getWeapon() {
        return this.weapon;
    }

    /**
     * Gets the dungeon
     * @return
     */
    public Dungeon getDungeon() {
        return dungeon;
    }

    /**
     * Gets the key the player currently is holding
     * @return
     */
    public int getKey() {
        return key;
    }

    public Key getKeyObject() {
        return keyObject;
    }

    /**
     * Set the key object the player is currently holding
     * @param key
     */
    public void setKeyObject(Key key) {
        this.keyObject = key;
    }

    /**
     * Sets the key the player is holding
     * @param pairCode
     */
    public void setKey(int pairCode) {
        this.key = pairCode;
    }

    /**
     * Handles player moving up
     */
    public void moveUp() {
        if (!dungeon.getState().equals("GameInProgress")) {
            return;
        }
        // Keep track of the last position the player has been before it moves
        lastX = getX();
        lastY = getY();
        // Make sure player does not go out of range
        if (getY() > 0) {
            setY(getY() - 1);
        }
        // Check if the square the player moves to causes an action to occur
        checkMoveToSquare();
    }

    /**
     * Handles player moving down
     */
    public void moveDown() {
        if (!dungeon.getState().equals("GameInProgress")) {
            return;
        }
        lastX = getX();
        lastY = getY();
        if (getY() < dungeon.getHeight() - 1) {
            setY(getY() + 1);
        }
        checkMoveToSquare();
    }

    /**
     * Handles player moving left
     */
    public void moveLeft() {
        if (!dungeon.getState().equals("GameInProgress")) {
            return;
        }
        lastX = getX();
        lastY = getY();
        if (getX() > 0) {
            setX(getX() - 1);
        }
        checkMoveToSquare();
    }

    /**
     * Handles player moving right
     */
    public void moveRight() {
        if (!dungeon.getState().equals("GameInProgress")) {
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
        // This player will always be on the same coords as player so do nothing
        return 0;
    }

    /**
     * Handles how player reacts when it hits an enemy
     */
    public void beAttacked() {
        if (this.potion == true) {
            return;
        } else if (this.weapon > 0) {
            this.weapon--;
            if (this.weapon == 0) {
                dungeon.removeFromInventory(weaponObject);
            }
        } else {
            // Call kill player function on the current game state.
            dungeon.killPlayer();
            // for(Entity e: dungeon.getEntities()) {
            //     if(e instanceof Enemy) {
            //         e.cancelTimer();
            //     }
            // }
        }
    }
    
    /**
     * Go through all entities and check if the player is touching it, handle accordingly
     */
    public void checkMoveToSquare() {
        for (Entity e : dungeon.getEntities()) {
            if (e.getX() == getX() && e.getY() == getY()) {
                int handlePlayerResult = e.handlePlayer(this);
                if (handlePlayerResult == 1) {
                    break;
                } 
            }
        }
        dungeon.checkSwitchedOn();
        dungeon.checkTreasureGoal();
        dungeon.checkEnemyGoal();
    }
}
