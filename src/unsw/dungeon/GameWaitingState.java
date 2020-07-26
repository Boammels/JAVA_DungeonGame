package unsw.dungeon;

public class GameWaitingState implements State{
    private Dungeon dungeon;
    
    public GameWaitingState(Dungeon dungeon) {
		this.dungeon = dungeon;
    }
    
    public void clearDungeon() {
        ;
    }

    public void die() {
        ;
    }

    @Override
    public String toString() {
        return "gameWaiting";
    }
}