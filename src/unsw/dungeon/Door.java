package unsw.dungeon;

public class Door extends Entity{
    private int pairCode;
    private Dungeon dungeon;
    private boolean open;

    public Door(Dungeon dungeon, int x, int y, int pairCode) {
        super(x,y);
        this.dungeon = dungeon;
        this.pairCode = pairCode;
        this.open = false;
    }

    public boolean isOpen() {
        return open;
    }

    public void openDoor() {
        open = true;
        //this.setShow(false);
    }

    public void closeDoor() {
        open = false;
    }

    @Override
    public int handlePlayer(Player p) {
        if(p.getKey() == this.pairCode && !this.open) {
            this.open = true;
            setShow(false);
            p.setKey(-1);
            dungeon.removeFromInventory(p.getKeyObject());
            p.setKeyObject(null);
        } else if (!this.open) {
            p.setX(p.getLastX());
            p.setY(p.getLastY());
            p.setLastX(getX());
            p.setLastY(getY());
        }
        return 1;
    }
    @Override
    public boolean isWall() {
        return !open;
    }
}