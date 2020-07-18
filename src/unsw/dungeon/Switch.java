package unsw.dungeon;

public class Switch extends Entity {
    private boolean switchedOn = false; 

    public Switch(int x, int y) {
        super(x, y);
    }

    public int handlePlayer(Player p) {
        // Will do nothing if the player is on, will do something however if a boulder is on,
        // possibly find a way to make this class more dynamic. 
        return 0;
    }
}