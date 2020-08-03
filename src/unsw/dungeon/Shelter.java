package unsw.dungeon;

public class Shelter extends Entity {
    
    private Dungeon dungeon;

    public Shelter(Dungeon dungeon, int x, int y) {
        super(x,y);
        this.dungeon = dungeon;
    }

    @Override
    public boolean isWall() {
        return true;
    }

    @Override
    public int handlePlayer(Player p) {
        return 1;
    }

    public void checkPlayerPosition(Player player) {
        if(player.getX() >= this.getX() - 2 && player.getX() <= this.getX() + 2) {
            if(player.getY() >= this.getY() - 2 && player.getY() <= this.getY() + 2) {
                setShow(false);
            } else {
                setShow(true);
            }
        } else {
            setShow(true);
        }
    }
}