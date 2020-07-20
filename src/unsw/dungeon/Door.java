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

    @Override
    public int handlePlayer(Player p) {
        if(p.getKey() == this.pairCode && !this.open) {
            this.open = true;
            p.setKey(-1);
            System.out.println("Door is Open");
            //what should we do when the door is open?
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