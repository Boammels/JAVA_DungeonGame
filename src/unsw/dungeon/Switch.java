package unsw.dungeon;

public class Switch extends Entity {
    private boolean switchedOn = false; 
    private Dungeon dungeon;

    /**
     * Create an instance of a switch positioned at square (x,y)
     * @param dungeon
     * @param x
     * @param y
     */
    public Switch(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.dungeon = dungeon;
    }

    @Override
    public int handlePlayer(Player p) {
        // If a player has managed to get on this switch, a boulder cannot be on it.
        switchedOn = false;
        return 0;
    }

    /**
     * Gets the whether this switch is currently turned on
     * @return
     */
    public boolean getSwitchedOn() {
        return switchedOn;
    }

    /**
     * Sets the switches turned on value
     * @param switchedOn
     */
    public void setSwitchedOn(boolean switchedOn) {
        this.switchedOn = switchedOn;
    }
}