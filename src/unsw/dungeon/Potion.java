package unsw.dungeon;

import java.util.Timer;
import java.util.TimerTask;

public class Potion extends Entity {
    private Dungeon dungeon;

    public Potion (Dungeon dungeon, int x, int y) {
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
        p.setPotion(true);
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