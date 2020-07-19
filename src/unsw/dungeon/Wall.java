package unsw.dungeon;

public class Wall extends Entity {

    /**
     * Create a wall at position (x, y)
     * @param x
     * @param y
     */
    public Wall(int x, int y) {
        super(x, y);
    }

    @Override
    public int handlePlayer(Player p) {
        // If the player hits a wall ,send the player back to its previous position
        // so it cannot go though it.
        p.setX(p.getLastX());
        p.setY(p.getLastY());
        p.setLastX(getX());
        p.setLastY(getY());
        return 0;
    }

    @Override 
    public boolean isWall(){
        return true;
    }
}
