package unsw.dungeon;

public class GameWaitingState implements State{
    private Dungeon dungeon;
    
    public GameWaitingState(Dungeon dungeon) {
		this.dungeon = dungeon;
    }
    
    public void clearDungeon() {
        dungeon.restart();
    }

    public void die() {
        dungeon.restart();
    }

    @Override
    public String toString() {
        return "gameWaiting";
    }
}