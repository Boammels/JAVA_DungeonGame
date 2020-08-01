package unsw.dungeon;

import java.util.Timer;
import java.util.TimerTask;

public class Potion extends Entity {
    private Dungeon dungeon;

    /**
     * Creates a potion at position (x, y)
     * @param dungeon
     * @param x
     * @param y
     */
    public Potion (Dungeon dungeon, int x, int y) {
        super(x,y);
        this.dungeon = dungeon;
    }

    /**
     * Handle what the potion does when it gets picked up
     */
    public void pickedup() {
        dungeon.getEntities().remove(this);
        setShow(false);
        // setX(0);
        // setY(0);
    }

    @Override
    public int handlePlayer(Player p) {
        p.setPotion(true);
        // Create a timer so the potitons effects stop after a certain amount of time has passed
        Timer timer = new Timer();
        timer.schedule(new TimerTask(){
            @Override
            public void run() {
                p.setPotion(false);
                timer.cancel();
            }
        },10000);
        this.pickedup();
        return 1;
    }
}