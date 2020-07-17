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

    @Override
    public int handlePlayer(Player p) {
        // TODO do we want to stack the hit points of weapons or cap them?
        p.setWeapon(p.getWeapon() + 5);
        System.out.println(p.getWeapon());
        this.pickedup();
        return 1;
    }
}