package unsw.dungeon;

public class Boulder extends Entity {

    public static final int LEFT = 1;
    public static final int RIGHT = 2;
    public static final int UP = 3;
    public static final int DOWN = 4;
    private Dungeon dungeon;
    ///private int portalDir;
    
    /**
     * Create an instance of a boulder positioned at square (x,y)
     * @param dungeon
     * @param x
     * @param y
     */
    public Boulder(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.dungeon = dungeon;
        //this.portalDir = 0;
    }

    @Override
    public int handlePlayer(Player p) {
        
        int xDiff = p.getX() - p.getLastX();
        int yDiff = p.getY() - p.getLastY();
        // Move the boulder in the same way the player has.
        setX(p.getX() + xDiff);
        setY(p.getY() + yDiff);
        
        int result = this.checkMoveToSquare();
        // Has this move made the boulder touch a square it cannot be?
        if (result == 2) {
            // Move the boulder back to its original square
            setX(getX() - xDiff);
            setY(getY() - yDiff);
            // Move the player back to its original sqaure
            p.setX(p.getLastX());
            p.setY(p.getLastY());
        } else if (result == 3) {
            setX(getX() + xDiff);
            setY(getY() + yDiff);
            result = checkMoveToSquare();
            if (result == 2) {
                // Move the boulder back to its original square
                setX(p.getX());
                setY(p.getY());
                // Move the player back to its original sqaure
                p.setX(p.getLastX());
                p.setY(p.getLastY());
            } else {
                // update switches and check goal
                dungeon.checkSwitchedOn();
                dungeon.checkSwitchGoal(); 
            }
        } else {
            // update switches and check goal
            dungeon.checkSwitchedOn();
            dungeon.checkSwitchGoal(); 
        }

        return 0;
        // Set the boulder move in the same way the player has to be what xDiff indicates
    }

    /**
     * Will handle what the boulder does when it has been pushed
     * @return 2 - hit a wall or another boulder, push back | 0 - nothing eventful | 3 - teleport boulder
     */
    private int checkMoveToSquare() {
        for (Entity e : dungeon.getEntities()) {
            if(e.getX() == getX() && e.getY() == getY() && e != this) {
                if (e instanceof Wall || e instanceof Boulder || e instanceof Enemy) {
                    return 2;
                } else if (e instanceof Portal) {
                    Portal p = (Portal)e;
                    p.teleportBoulder(this);
                   return 3;
                }
            }
        }
        return 0;
    }

    @Override 
    public boolean isWall(){
        return true;
    }
}