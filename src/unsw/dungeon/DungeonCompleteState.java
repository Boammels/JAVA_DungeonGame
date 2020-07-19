package unsw.dungeon;

public class DungeonCompleteState implements State{
    private Dungeon dungeon;
    
    public DungeonCompleteState(Dungeon dungeon) {
		this.dungeon = dungeon;
    }
    
    public void clearDungeon() {
        return;
    }

    public void die() {
        return;
    }

    @Override
    public String toString() {
        return "DungeonComplete";
    }
}