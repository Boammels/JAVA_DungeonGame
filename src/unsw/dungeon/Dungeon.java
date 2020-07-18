/**
 *
 */
package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

/**
 * A dungeon in the interactive dungeon player.
 *
 * A dungeon can contain many entities, each occupy a square. More than one
 * entity can occupy the same square.
 *
 * @author Robert Clifton-Everest
 *
 */
public class Dungeon {

    private int width; 
    private int height;
    private List<Entity> entities;
    private List<Switch> switches;
    private List <Boulder> boulders;
    private Player player;
    // Store as an int so we can differentiate in the future between successful completion and a game over
    private List<String> goals;
    private int gameStatus;

    private static final int IN_PROGRESS = 0;
    private static final int GAME_OVER = -1;
    private static final int GAME_COMPLETE = 1;

    public Dungeon(int width, int height) {
        this.width = width;
        this.height = height;
        this.entities = new ArrayList<>();
        this.switches = new ArrayList<>();
        this.boulders = new ArrayList<>();
        this.player = null;
        this.goals = new ArrayList<>();
        this.gameStatus = IN_PROGRESS;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
        if (entity instanceof Switch) {
            switches.add((Switch) entity);
        } else if (entity instanceof Boulder) {
            boulders.add((Boulder) entity);
        }
    }

    public void addGoal(String goal) {
        goals.add(goal);
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public List<Switch> getAllSwitches () {
        return switches;
    }

    public void setGameStatus(int gameStatus) {
        this.gameStatus = gameStatus;
    }

    public int getGameStatus() {
        return gameStatus;
    }

    public void checkSwitchedOn() {
        // called initially to see if any boulders start on switches
        // slow process - but only called once at the beginning, so should not impact performance
        for (Switch s : switches) {
            for (Boulder b : boulders) {
                if (b.getX() == s.getX() && b.getY() == s.getY()) {
                    s.setSwitchedOn(true);
                }
            }
        }
    }

    // Checks if all switches in the dungeon are turned on
    public boolean checkAllOn() {
        for (Switch s : getAllSwitches()) {
            if (!s.getSwitchedOn()) {
                return false;
            }
        }
        return true;
    }

    public void completeGoal(String goalCompleted) {
        //This will handle ALL goals must be compelted to finish dungeon
        // TODO handle OR goals
        if (goals.contains(goalCompleted)) {
            goals.remove(goalCompleted);
            if (goals.isEmpty()) {
                setGameStatus(GAME_COMPLETE);
            }
        }
    }

}
