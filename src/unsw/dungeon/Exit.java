package unsw.dungeon;

public class Exit extends Entity {
    
    private Dungeon dungeon;

    public Exit(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.dungeon = dungeon;
    }

    public void handlePlayer(Player p) {
        // If the player is on the exit, clear that objective and check if the dungeon is complete
        // Work out a nice way to handle the potential logical goal conditions reusing the endGame function
        dungeon.endGame();
    }
}