package unsw.dungeon;

public class Boulder extends Entity {
    private Dungeon dungeon;
    
    public Boulder(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.dungeon = dungeon;
    }

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
        // Has the boulder hit a switch that has completed the game?
        } else if (result == 1) {
            // Set this goal to complete in the dungeon
            dungeon.completeGoal("boulders");
        }

        return 0;
        // Set the boulder move in the same way the player has to be what xDiff indicates
    }

    /**
     * Will handle what the boulder does when it has been pushed
     * @return 2 - hit a wall or another boulder, push back | 1 - boulder move successfully completed an objective | 0 - nothing eventful
     */
    private int checkMoveToSquare() {
        Switch checkSwitch = null;
        for (Entity e : dungeon.getEntities()) {
            if(e.getX() == getX() && e.getY() == getY() && e != this) {
                if (e instanceof Wall || e instanceof Boulder || e instanceof Enemy) {
                    return 2;
                } else if (e instanceof Switch) {
                    checkSwitch = (Switch) e;
                }
            }
        }
        if (checkSwitch != null) {
            checkSwitch.setSwitchedOn(true);
            if (dungeon.checkAllOn()) {
                return 1;
            }
            
        }
        return 0;
    }
}