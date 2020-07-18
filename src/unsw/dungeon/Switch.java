package unsw.dungeon;

public class Switch extends Entity {
    private boolean switchedOn = false; 
    private Dungeon dungeon;

    public Switch(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.dungeon = dungeon;
    }

    public int handlePlayer(Player p) {
        // If a player has managed to get on this switch, a boulder cannot be on it.
        switchedOn = false;
        return 0;
    }

    public boolean getSwitchedOn() {
        return switchedOn;
    }

    public void setSwitchedOn(boolean switchedOn) {
        this.switchedOn = switchedOn;
    }
}