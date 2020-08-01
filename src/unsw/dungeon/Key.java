package unsw.dungeon;

public class Key extends Entity{
    
    private int pairCode;
    private Dungeon dungeon;

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
    }

    /**
     * Handles a key getting collected
     */
    public void pickedup() {
        dungeon.getEntities().remove(this);
        setShow(false);
        // setX(0);
        // setY(0);
    }

    @Override
    public int handlePlayer(Player p) {
        // Player can only pick up a key if they are not already holding one
        if(p.getKey() == -1) {
            p.setKey(pairCode);
            this.pickedup();
        }
        return 1;
    }
}