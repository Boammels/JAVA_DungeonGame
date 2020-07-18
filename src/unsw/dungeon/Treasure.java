package unsw.dungeon;

public class Treasure extends Entity{
    private Dungeon dungeon;

    public Treasure (Dungeon dungeon, int x, int y) {
        super (x,y);
        this.dungeon = dungeon;
    }

    public void pickedup() {
        // System.out.println(dungeon.getTreasures().size());
        dungeon.getEntities().remove(this);
        // System.out.println(dungeon.getTreasures().size());
        dungeon.decreaseTreasureCount();
        setX(0);
        setY(0);
    }

    @Override
    public int handlePlayer(Player p) {
        this.pickedup();
        return 1;
    }
    
}