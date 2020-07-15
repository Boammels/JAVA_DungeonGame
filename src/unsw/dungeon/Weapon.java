package unsw.dungeon;

public class Weapon extends Entity{
    private Dungeon dungeon;

    public Weapon (Dungeon dungeon, int x, int y) {
        super(x,y);
        this.dungeon = dungeon;
    }

    public void pickedup() {
        dungeon.getEntities().remove(this);
    }
}