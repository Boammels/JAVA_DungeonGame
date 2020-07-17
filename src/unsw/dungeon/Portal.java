package unsw.dungeon;

public class Portal extends Entity{

    private Dungeon dungeon;
    private Portal exit;

    public Portal (Dungeon dungeon, int x,int y) {
        super(x,y);
        this.dungeon = dungeon;
        this.exit = null;
        for(Entity e :dungeon.getEntities()) {
            e.addPortal(this);
        }
    }

    @Override
    public void addPortal(Portal exit){
        exit.linkPortal(this);
        linkPortal(exit);
    }

    public void linkPortal (Portal exit) {
        this.exit = exit;
    }

    public int getExitX() {
        return exit.getX();
    }

    public int getExitY() {
        return exit.getY();
    }

    public Portal getExit() {
        return exit;
    }

    @Override
    public int handlePlayer(Player p) {
        if(this.getExit() == null) {
            return 1;
        }
        int newX = this.getExitX();
        int newY = this.getExitY();
        p.setX(newX);
        p.setY(newY);
        return 1;
    }
}