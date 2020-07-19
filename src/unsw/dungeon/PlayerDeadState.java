package unsw.dungeon;

public class PlayerDeadState implements State {
    private Dungeon dungeon;
    
    public PlayerDeadState(Dungeon dungeon) {
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
        return "PlayerDead";
    }
}