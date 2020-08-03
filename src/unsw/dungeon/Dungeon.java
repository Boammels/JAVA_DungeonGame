/**
 *
 */
package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

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
    private List <Enemy> enemies = new ArrayList<>();
    private int initTreasureCount = 0;
    private int treasureCount = 0;
    private int initEnemyCount = 0;
    private int enemyCount = 0;
    private Player player;
    private List <Entity> inventory = new ArrayList<>();
    private List<StateObserver> observers = new ArrayList<>();
    // Limit the use the controller in this class, only call methods
    // private DungeonController controller;
    // Store as an int so we can differentiate in the future between successful completion and a game over
    // private List<String> goals;
    private GoalComponent goals;
    private StringProperty goalText = new SimpleStringProperty();
    // private int gameStatus;
    State dungeonCompleteState = new DungeonCompleteState(this);
    State gameInProgressState = new GameInProgressState(this);
    State playerDeadState = new PlayerDeadState(this);
    State gameWaitingState = new GameWaitingState(this);
    State state;

    /**
     * Create a dungeon of size (width, height)
     * @param width
     * @param height
     */
    public Dungeon(int width, int height) {
        this.width = width;
        this.height = height;
        this.player = null;
        this.state = gameWaitingState;
        // this.gameStatus = IN_PROGRESS;
    }

    public State getDungeonCompleteState() {
        return dungeonCompleteState;
    }

    public State getPlayerDeadState() {
        return playerDeadState;
    }

    public State getDungeonInProgressState() {
        return gameInProgressState;
    }

    // public void setController(DungeonController dc) {
    //     controller = dc;
    // }

    public void attatchObserver(StateObserver o) {
        if (!observers.contains(o)) {
            observers.add(o);
        }
    }

    public void setState(State state) {
        this.state = state;
        for (StateObserver o : observers) {
            o.updateState(this);
        }
        // controller.handleStateChange();
    }

    public String getState() {
        return state.toString();
    }

    public void killPlayer() {
        state.die();
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

    /**
     * Add an entity to the inventory and update the inventory display
     * @param e
     */
    public void moveToInventory(Entity e) {
        inventory.add(e);
        int i = 0;
        for (Entity entity : inventory) {
            entity.setX(i);
            entity.setY(0);
            i++;
        }
    }

    /**
     * Remove an item from the inventory and update the inventory display
     * @param e
     */
    public void removeFromInventory(Entity e) {
        e.setShow(false);
        inventory.remove(e);
        int i = 0;
        for (Entity entity : inventory) {
            entity.setX(i);
            entity.setY(0);
            i++;
        }
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
        if (entity instanceof Switch) {
            switches.add((Switch) entity);
        } else if (entity instanceof Boulder) {
            boulders.add((Boulder) entity);
        } else if (entity instanceof Treasure) {
            treasureCount++;
            initTreasureCount = treasureCount;
        } else if (entity instanceof Enemy) {
            enemies.add((Enemy)entity);
            enemyCount++;
            initEnemyCount = enemyCount;
        }
    }

    public void stopEnemies() {
        for (Enemy e : enemies) {
            e.stop();
        }
    }

    /**
     * Function is called when a this dungeon is switched to
     */
    public void start() {
        restart();
        setState(getDungeonInProgressState());
        // Make sure when we start up a dungeon: all the enemies are moving
        for (Enemy e : enemies) {
            e.go();
        }
    }

    /**
     * Function which restarts the state of the dungeon back to its original
     */
    public void restart() {
        // All goals that may have been completed are turned back to uncompleted
        goals.clear();
        goalText.set("GOALS: " + goals.getGoals());
        player.setWeapon(0);
        player.setPotion(false);
        player.setKey(-1);
        enemyCount = initEnemyCount;
        inventory.clear();
        treasureCount = initTreasureCount;
        // For all entities, move them back to there original position, make sure they are all being displayed
        // Handle the special cases for certain objects
        for (Entity e : entities) {
            e.setX(e.getInitX());
            e.setY(e.getInitY());
            e.setShow(true);
            if (e instanceof Door) {
                Door d = (Door)e;
                d.closeDoor();
            } else if (e instanceof Collectable) {
                Collectable c = (Collectable)e;
                c.setPickedUp(false);
            }
        }
    }

    public void addGoal(Goal goal) {
        goals = goal;
    }
    public void addGoal(GoalGroup goal) {
        goals = goal;
    }

    public StringProperty getGoalText() {
        return goalText;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public List<Switch> getAllSwitches () {
        return switches;
    }

    /**
     * For each switch, turns it on if a boulder is on it
     */
    public void checkSwitchedOn() {
        for (Switch s : switches) {
            boolean toTurnOn = false;
            for (Boulder b : boulders) {
                if (b.getX() == s.getX() && b.getY() == s.getY()) {
                    // s.setSwitchedOn(true);
                    toTurnOn = true;
                }
            }
            s.setSwitchedOn(toTurnOn);
        }
    }

    
    // Checks if all switches in the dungeon are turned on
    public void checkSwitchGoal() {
        boolean allOn = true;
        for (Switch s : switches) {
            if (!s.getSwitchedOn()) {
                allOn = false;
            }
        }
        if (allOn) {
            completeGoal("boulders");
        }
    }

    /**
     * Handles player interaction for switch?
     */
    public void updateSwitches() {
        for (Switch s : switches) {
            s.handlePlayer(getPlayer());
        }
    }
    
    /**
     * Check if all treasure on the map has been collected
     */
    public void checkTreasureGoal() {
        if (treasureCount == 0) {
            completeGoal("treasure");
        }
    }
    
    /**
     * Treasure has been collected, decrease its count
     */
    public void decreaseTreasureCount() {
        treasureCount--;
    }

    public int getTreasureCount() {
        return treasureCount;
    }
    
    /**
     * Checks if the enemy goal of defeating all enemies has been completed
     */
    public void checkEnemyGoal() {
        if (enemyCount == 0) {
            completeGoal("enemies");
        }
    }

    /**
     * lowers the count of enemies currently alive by one
     */
    public void decreaseEnemyCount() {
        enemyCount--;
    }

    public int getEnemyCount() {
        return enemyCount;
    }

    /**
     * Completes a goal and checks if this goal completion has completed the dungeon
     * @param goalCompleted
     */
    public void completeGoal(String goalCompleted) {
        // Finish the goal, the return value will indicate whether this change resulted in
        // the dungeon completing
        boolean allComplete = goals.complete(goalCompleted);
        if (allComplete) {
            state.clearDungeon();
        }
    }

}
