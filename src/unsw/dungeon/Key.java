package unsw.dungeon;

public class Key extends Entity implements Collectable{
    
    private int pairCode;
    private Dungeon dungeon;
    private boolean pickedUp;

    /**
     * Create a key at position (x, y) with a pairCode
     * @param dungeon
     * @param x
     * @param y
     * @param pairCode
     */
    public Key(Dungeon dungeon, int x, int y, int pairCode) {
        super(x,y);
        this.dungeon = dungeon;
        this.pairCode = pairCode;
        this.pickedUp = false;
    }

    public void setPickedUp(boolean value) {
        this.pickedUp = value;
    }

    /**
     * Handles a key getting collected
     */
    public void pickUp() {
        // dungeon.getEntities().remove(this);
        // setShow(false);
        dungeon.moveToInventory(this);
        // setX(0);
        // setY(0);
    }

    @Override
    public int handlePlayer(Player p) {
        // Player can only pick up a key if they are not already holding one
        if (!pickedUp) {
            if (p.getKey() == -1) {
                p.setKey(pairCode);
                p.setKeyObject(this);
                this.pickUp();
            }
        }
        return 1;
    }
}