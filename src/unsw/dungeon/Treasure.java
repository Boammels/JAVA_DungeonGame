package unsw.dungeon;

public class Treasure extends Entity{
    private Dungeon dungeon;

    public Treasure (Dungeon dungeon, int x, int y) {
        super (x,y);
        this.dungeon = dungeon;
    }

    public void pickedup() {
        dungeon.getEntities().remove(this);
    }

    public void handlePlayer(Player p) {
        this.pickedup();
    }
    
}