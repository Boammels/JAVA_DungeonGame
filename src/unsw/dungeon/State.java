package unsw.dungeon;

/**
 * Holds the state the game is currently in, 
 * for now, quite basic, however in the future
 * it will account for menu states, going between dungeon states ect.
 */
public interface State {
    public void clearDungeon();
    public void die();
}