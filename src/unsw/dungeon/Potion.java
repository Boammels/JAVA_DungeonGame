package unsw.dungeon;

public class Potion extends Entity{
    private Dungeon dungeon;

    public Potion (Dungeon dungeon, int x, int y) {
        super(x,y);
        this.dungeon = dungeon;
    }

    public void pickedup() {
        dungeon.getEntities().remove(this);
    }

    @Override
    public int handlePlayer(Player p) {
        p.setPotion(true);
        this.pickedup();
        return 1;
    }
}