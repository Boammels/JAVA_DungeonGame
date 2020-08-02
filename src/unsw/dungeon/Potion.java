package unsw.dungeon;

import java.util.Timer;
import java.util.TimerTask;

public class Potion extends Entity implements Collectable{
    private Dungeon dungeon;
    private boolean pickedUp;

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

    @Override
    public int handlePlayer(Player p) {
        if (!pickedUp) {
            p.setPotion(true);
            pickedUp = true;
            // Create a timer so the potitons effects stop after a certain amount of time has passed
            Timer timer = new Timer();
            Potion thisPotion = this;
            timer.schedule(new TimerTask(){
                @Override
                public void run() {
                    p.setPotion(false);
                    timer.cancel();
                    dungeon.removeFromInventory(thisPotion);
                }
            },10000);
            this.pickUp();
        }
        return 1;
    }
}