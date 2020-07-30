package unsw.dungeon;

public class HiddenBomb extends Entity{

    private Dungeon dungeon;

    public HiddenBomb(Dungeon dungeon, int x, int y) {
        super(x,y);
        this.dungeon = dungeon;
    }

    public void activate() {
        dungeon.getEntities().remove(this);
    }

    @Override
    public int handlePlayer(Player player) {
        activate();
        dungeon.killPlayer();
        return 1;
    }

    
    
}