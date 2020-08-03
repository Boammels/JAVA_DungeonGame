package unsw.dungeon;

public class Weapon extends Entity implements Collectable{
    private int hitPoints;
    private Dungeon dungeon;
    private boolean pickedUp;

    /**
     * Create a weapon at position (x, y)
     * @param dungeon
     * @param x
     * @param y
     */
    public Weapon (Dungeon dungeon, int x, int y, int hitPoints) {
        super(x,y);
        this.hitPoints = hitPoints;
        this.dungeon = dungeon;
        this.pickedUp = false;
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
        if (!pickedUp && !p.haveWeapon()) {
            pickedUp = true;
            p.setWeapon(p.getWeapon() + hitPoints);
            p.setWeaponObject(this);
            this.pickUp();
        }
        return 1;
    }
}