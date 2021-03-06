package unsw.dungeon;

import java.util.Timer;
import java.util.TimerTask;

public class Potion extends Entity implements Collectable{
    private Dungeon dungeon;
    private boolean pickedUp;
    private Timer timer;

    /**
     * Creates a potion at position (x, y)
     * @param dungeon
     * @param x
     * @param y
     */
    public Potion (Dungeon dungeon, int x, int y) {
        super(x,y);
        this.dungeon = dungeon;
        this.pickedUp = false;
    }

    /**
     * Handle what the potion does when it gets picked up
     */
    public void pickUp() {
        // dungeon.getEntities().remove(this);
        dungeon.moveToInventory(this);
        // setX(0);
        // setY(0);
    }

    public void setPickedUp(boolean value) {
        this.pickedUp = value;
    }

    public Timer getTimer() {
        return timer;
    }

    @Override
    public int handlePlayer(Player p) {
        if (!pickedUp) {
            p.setPotion(true);
            pickedUp = true;
            // Create a timer so the potitons effects stop after a certain amount of time has passed
            timer = new Timer();
            Potion thisPotion = this;
            timer.schedule(new TimerTask(){
                @Override
                public void run() {
                    timer.cancel();
                    if (p.havePotion()) {
                        p.setPotion(false);
                        dungeon.removeFromInventory(thisPotion);
                    }
                }
            },10000);
            this.pickUp();
        }
        return 1;
    }
}