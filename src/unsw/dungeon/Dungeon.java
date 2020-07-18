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

    private int width, height;
    private List<Entity> entities = new ArrayList<>();
    private List<Switch> switches = new ArrayList<>();
    private List <Boulder> boulders = new ArrayList<>();
    private int treasureCount = 0;
    private int enemyCount = 0;
    private Player player;
    // Store as an int so we can differentiate in the future between successful completion and a game over
    // private List<String> goals;
    private GoalComponent goals;
    private int gameStatus;

    private static final int IN_PROGRESS = 0;
    private static final int GAME_OVER = -1;
    private static final int GAME_COMPLETE = 1;

    public Dungeon(int width, int height) {
        this.width = width;
        this.height = height;
        // this.entities ;
        // this.switches = new ArrayList<>();
        // this.boulders = new ArrayList<>();
        this.player = null;
        // this.goals = new ArrayList<>();
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
        } else if (entity instanceof Treasure) {
            treasureCount++;
        } else if (entity instanceof Enemy) {
            enemyCount++;
        }
    }

    public void addGoal(Goal goal) {
        goals = goal;
    }
    public void addGoal(GoalGroup goal) {
        goals = goal;
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
    public void checkSwitchGoal() {
        for (Switch s : switches) {
            if (!s.getSwitchedOn()) {
                return;
            }
        }
        completeGoal("boulders");
    }
    
    public void checkTreasureGoal() {
        if (treasureCount == 0) {
            System.out.println("treasure goal completed");
            completeGoal("treasure");
        }
    }
    
    public void decreaseTreasureCount() {
        treasureCount--;
    }
    
    public void checkEnemyGoal() {
        if (enemyCount == 0) {
            System.out.println("enemy goal complete");
            completeGoal("enemies");
        }
    }

    public void decreaseEnemyCount() {
        enemyCount--;
    }

    public void completeGoal(String goalCompleted) {
        //This will handle ALL goals must be compelted to finish dungeon
        boolean change = goals.complete(goalCompleted);
        // if (!(goals instanceof Goal)) {
        //     goals.isLayerComplete();
        // }
        int result = goals.getSize();
        // No uncompleted goals at the top level...
        if (result == 0) {
            setGameStatus(GAME_COMPLETE);
        }
        // if (goals.contains(goalCompleted)) {
        //     goals.remove(goalCompleted);
        //     if (goals.isEmpty()) {
        //         setGameStatus(GAME_COMPLETE);
        //     }
        // }
    }

}
