package unsw.dungeon;

public class GameInProgressState implements State {
    private Dungeon dungeon;
    
    public GameInProgressState(Dungeon dungeon) {
		this.dungeon = dungeon;
    }
    
    public void clearDungeon() {
        dungeon.restart();
        dungeon.setState(dungeon.getDungeonCompleteState());
    }

    public void die() {
        // dungeon.restart();
        dungeon.setState(dungeon.getPlayerDeadState());
    }

    @Override
    public String toString() {
        return "GameInProgress";
    }
}