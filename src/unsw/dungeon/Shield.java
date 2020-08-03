package unsw.dungeon;

public class Shield extends Entity implements Collectable {
    private Dungeon dungeon;
    private boolean pickedUp;

    public Shield (Dungeon dungeon, int x, int y) {
        super(x,y);
        this.dungeon = dungeon;
    }

    /**
     * Handle what happens to a sword when it gets picked up
     */
    public void pickUp() {
        // dungeon.getEntities().remove(this);
        // setShow(false);
        dungeon.moveToInventory(this);
        // setX(0);
        // setY(0);
    }

    public void setPickedUp(boolean value) {
        this.pickedUp = value;
    }

    @Override
    public int handlePlayer(Player p) {
        if (!pickedUp) {
            pickedUp = true;
            p.setShield(p.getShield() + 2);
            p.setShieldObject(this);
            this.pickUp();
        }
        return 1;
    }
}