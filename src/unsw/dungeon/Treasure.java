package unsw.dungeon;

public class Treasure extends Entity{
    private Dungeon dungeon;
    private boolean pickedUp;

    /**
     * Create treasure at position (x, y)
     * @param dungeon
     * @param x
     * @param y
     */
    public Treasure (Dungeon dungeon, int x, int y) {
        super (x,y);
        this.dungeon = dungeon;
        this.pickedUp = false;
    }

    /**
     * Handle what happens to treasure when it is picked up
     */
    public void pickedup() {
        // dungeon.getEntities().remove(this);
        dungeon.decreaseTreasureCount();
        dungeon.moveToInventory(this);
        // setShow(false);
        // setX(0);
        // setY(0);
    }

    @Override
    public int handlePlayer(Player p) {
        if (!pickedUp) {
            pickedUp = true;
            this.pickedup();
        }
        return 1;
    }
    
}