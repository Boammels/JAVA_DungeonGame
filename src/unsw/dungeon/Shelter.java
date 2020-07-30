package unsw.dungeon;

public class Shelter extends Entity {
    
    private Dungeon dungeon;

    public Shelter(Dungeon dungeon, int x, int y) {
        super(x,y);
        this.dungeon = dungeon;
    }

    @Override
    public boolean isWall() {
        return true;
    }

    @Override
    public int handlePlayer(Player p) {
        return 1;
    }
}