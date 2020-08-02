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
    private List <Enemy> enemies = new ArrayList<>();
    private int initTreasureCount = 0;
    private int treasureCount = 0;
    private int initEnemyCount = 0;
    private int enemyCount = 0;
    private Player player;
    private List <Entity> inventory = new ArrayList<>();
    // Limit the use the controller in this class, only call methods
    private DungeonController controller;
    // Store as an int so we can differentiate in the future between successful completion and a game over
    // private List<String> goals;
    private GoalComponent goals;
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

    public void setController(DungeonController dc) {
        controller = dc;
    }

    public void setState(State state) {
        this.state = state;
        controller.handleStateChange();
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

    public void moveToInventory(Entity e) {
        // e.setX(inventorySlot);
        // e.setY(0);
        // inventorySlot++;
        inventory.add(e);
        int i = 0;
        for (Entity entity : inventory) {
            entity.setX(i);
            entity.setY(0);
            i++;
        }
    }

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

    public void start() {
        restart();
        setState(getDungeonInProgressState());
        for (Enemy e : enemies) {
            e.go();
        }
    }

    /**
     * Function which restarts the state of the dungeon back to its original
     */
    public void restart() {
        goals.clear();
        player.setWeapon(0);
        player.setPotion(false);
        player.setKey(-1);
        enemyCount = initEnemyCount;
        treasureCount = initTreasureCount;
        for (Entity e : entities) {
            e.setX(e.getInitX());
            e.setY(e.getInitY());
            e.setShow(true);
            if (e instanceof Door) {
                Door door = (Door)e;
                door.closeDoor();
            }
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

    // public void setGameStatus(int gameStatus) {
    //     this.gameStatus = gameStatus;
    // }

    // public int getGameStatus() {
    //     return gameStatus;
    // }

    public void checkSwitchedOn() {
        // called initially to see if any boulders start on switches
        // slow process - but only called once at the beginning, so should not impact performance
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
            // System.out.println(s.getSwitchedOn());
            if (!s.getSwitchedOn()) {
                allOn = false;
            }
        }
        // System.out.println("\n");
        if (allOn) {
            completeGoal("boulders");
        }
    }

    public void updateSwitches() {
        for (Switch s : switches) {
            s.handlePlayer(getPlayer());
        }
    }
    
    public void checkTreasureGoal() {
        System.out.println(treasureCount);
        if (treasureCount == 0) {
            completeGoal("treasure");
        }
    }
    
    public void decreaseTreasureCount() {
        treasureCount--;
    }

    public int getTreasureCount() {
        return treasureCount;
    }
    
    public void checkEnemyGoal() {
        if (enemyCount == 0) {
            completeGoal("enemies");
        }
    }

    public void decreaseEnemyCount() {
        enemyCount--;
    }

    public int getEnemyCount() {
        return enemyCount;
    }

    public void completeGoal(String goalCompleted) {
        // Finish the goal, the return value will indicate whether this change resulted in
        // the dungeon completing
        boolean allComplete = goals.complete(goalCompleted);
        if (allComplete) {
            state.clearDungeon();
        }
    }

}
