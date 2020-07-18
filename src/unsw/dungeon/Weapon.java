package unsw.dungeon;

public class Weapon extends Entity{
    public static final int WEAPONSIZE = 5;
    private Dungeon dungeon;

    public Weapon (Dungeon dungeon, int x, int y) {
        super(x,y);
        this.dungeon = dungeon;
    }

    public void pickedup() {
        dungeon.getEntities().remove(this);
        setX(0);
        setY(0);
    }

    @Override
    public int handlePlayer(Player p) {
        p.setWeapon(p.getWeapon() + WEAPONSIZE);
        this.pickedup();
        return 1;
    }
}