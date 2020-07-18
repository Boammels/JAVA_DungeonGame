package unsw.dungeon;

public class Boulder extends Entity {
    private Dungeon dungeon;
    
    public Boulder(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.dungeon = dungeon;
    }

    public int handlePlayer(Player p) {
        // Have to see where the player is coming from so we know the direction the boulder should move in,
        // also need to be aware that the boulder shouldnt move if there is a wall in front
        int xDiff = p.getX() - p.getLastX();
        int yDiff = p.getY() - p.getLastY();
        // Ideally want to move the boulder first, see if it is allowed, then decide what happens to the player
        // must consider pushing boulder into wall
        setX(p.getX() + xDiff);
        setY(p.getY() + yDiff);
        return 0;
        // Set the boulder move in the same way the player has to be what xDiff indicates
    }
}