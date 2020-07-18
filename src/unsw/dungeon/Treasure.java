package unsw.dungeon;

public class Treasure extends Entity{
    private Dungeon dungeon;

    public Treasure (Dungeon dungeon, int x, int y) {
        super (x,y);
        this.dungeon = dungeon;
    }

    public void pickedup() {
        dungeon.getEntities().remove(this);
        setX(0);
        setY(0);
    }

    @Override
    public int handlePlayer(Player p) {
        this.pickedup();
        return 1;
    }
    
}