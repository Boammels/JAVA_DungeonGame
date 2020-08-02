package unsw.dungeon;

public class Portal extends Entity{

    private Dungeon dungeon;
    private Portal exit;

    /**
     * Creates a portal at position (x, y)
     * @param dungeon
     * @param x
     * @param y
     */
    public Portal (Dungeon dungeon, int x, int y) {
        super(x,y);
        this.dungeon = dungeon;
        this.exit = null;
        for (Entity e : this.dungeon.getEntities()) {
            e.addPortal(this);
        }
    }

    @Override
    public void addPortal(Portal exit){
        exit.linkPortal(this);
        linkPortal(exit);
    }

    /**
     * Sets the portal that this one is connected to
     * @param exit
     */
    public void linkPortal (Portal exit) {
        this.exit = exit;
    }

    /**
     * Gets the x coordinate of the exit portal (the one that you get teleported to when entering this one)
     * @return
     */
    public int getExitX() {
        return exit.getX();
    }

    /**
     * Gets the y coordinate of the exit portal
     * @return
     */
    public int getExitY() {
        return exit.getY();
    }

    /**
     * Gets the entire exit portal
     * @return
     */
    public Portal getExit() {
        return exit;
    }

    @Override
    public int handlePlayer(Player p) {
        if (this.getExit() == null) {
            return 1;
        }
        // If this portal has a portal it is connected to, set the player to its coordinates
        int newX = this.getExitX();
        int newY = this.getExitY();
        p.setX(newX);
        p.setY(newY);
        return 1;
    }
    @Override
    public void teleportEnemy(Enemy enemy) {
        if(getExit() != null) {
            enemy.setX(getExitX());
            enemy.setY(getExitY());
        }
    }

    @Override
    public void teleportBoulder(Boulder boulder) {
        if(getExit() != null) {
            boulder.setX(getExitX());
            boulder.setY(getExitY());
        }
    }
}