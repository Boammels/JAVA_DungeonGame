package unsw.dungeon;

public class Wall extends Entity {

    public Wall(int x, int y) {
        super(x, y);
    }

    public void handlePlayer(Player p) {
        p.setX(p.getLastX());
        p.setY(p.getLastY());
        p.setLastX(getX());
        p.setLastY(getY());
    }
}
