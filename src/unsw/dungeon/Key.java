package unsw.dungeon;

public class Key extends Entity{
    
    private int pairCode;
    private Dungeon dungeon;

    public Key(Dungeon dungeon, int x, int y, int pairCode) {
        super(x,y);
        this.dungeon = dungeon;
        this.pairCode = pairCode;
    }

    public void pickedup() {
        dungeon.getEntities().remove(this);
        setX(0);
        setY(0);
    }

    @Override
    public int handlePlayer(Player p) {
        if(p.getKey() == -1) {
            p.setKey(pairCode);
            this.pickedup();
        }
        return 1;
    }
}