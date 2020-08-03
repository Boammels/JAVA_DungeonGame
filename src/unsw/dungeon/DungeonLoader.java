package unsw.dungeon;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Loads a dungeon from a .json file.
 *
 * By extending this class, a subclass can hook into entity creation. This is
 * useful for creating UI elements with corresponding entities.
 *
 * @author Robert Clifton-Everest
 *
 */
public abstract class DungeonLoader {

    private JSONObject json;

    public DungeonLoader(String filename) throws FileNotFoundException {
        json = new JSONObject(new JSONTokener(new FileReader("dungeons/" + filename)));
    }

    /**
     * Parses the JSON to create a dungeon.
     * @return
     */
    public Dungeon load() {
        int width = json.getInt("width");
        int height = json.getInt("height");

        Dungeon dungeon = new Dungeon(width, height);

        JSONArray jsonEntities = json.getJSONArray("entities");

        for (int i = 0; i < jsonEntities.length(); i++) {
            loadEntity(dungeon, jsonEntities.getJSONObject(i));
        }
        // Must do an initial check on all switches to turn them on if boulders are on them.
        dungeon.checkSwitchedOn();
        JSONObject jsonGoals = json.getJSONObject("goal-condition");
        String type = jsonGoals.getString("goal");
        if (type.equals("OR") || type.equals("AND")) {
            GoalGroup allGoals = new GoalGroup(type);
            allGoals = loadGoals(jsonGoals, allGoals);
            dungeon.addGoal(allGoals);
        } else {
            Goal allGoals = new Goal(type);
            dungeon.addGoal(allGoals);
        }
        return dungeon;
    }

    /**
     * Loads in an entity to the backend in accordance with its json object
     * @param dungeon - dungeon the entity will be added to
     * @param json - the json obect the contains the information about the entity
     */
    private void loadEntity(Dungeon dungeon, JSONObject json) {
        String type = json.getString("type");
        int x = json.getInt("x");
        int y = json.getInt("y");

        Entity entity = null;
        switch (type) {
        case "player":
            Player player = new Player(dungeon, x, y);
            dungeon.setPlayer(player);
            onLoad(player);
            entity = player;
            break;
        case "wall":
            Wall wall = new Wall(x, y);
            onLoad(wall);
            entity = wall;
            break;
        case "exit":
            Exit exit = new Exit(dungeon, x, y);
            onLoad(exit);
            entity = exit;
            break;
        case "boulder":
            Boulder boulder = new Boulder(dungeon, x, y);
            onLoad(boulder);
            entity = boulder;
            break;
        case "switch":
            Switch switchObject = new Switch(dungeon, x, y);
            onLoad(switchObject);
            entity = switchObject;
            break;
        case "invincibility":
            Potion potion = new Potion(dungeon, x, y);
            onLoad(potion);
            entity = potion;
            break;
        case "treasure":
            Treasure treasure = new Treasure(dungeon, x, y);
            onLoad(treasure);
            entity = treasure;
            break;
        case "portal":
            Portal portal = new Portal(dungeon, x, y);
            onLoad(portal);
            entity = portal;
            break;
        case "woodenSword":
            Weapon woodSword = new Weapon(dungeon, x, y, 1);
            onLoad(woodSword, 0);
            entity = woodSword;
            break;
        case "sword":
            Weapon weapon = new Weapon(dungeon, x, y, 5);
            onLoad(weapon, 1);
            entity = weapon;
            break;
        case "enemy":
            Enemy enemy = new Enemy(dungeon, dungeon.getPlayer(), x, y, 1000);
            onLoad(enemy, 0);
            entity = enemy;
            break;
        case "wolf":
            Enemy wolf = new Enemy(dungeon, dungeon.getPlayer(), x, y, 400);
            onLoad(wolf, 1);
            entity = wolf;
            break;
        case "door":
            Door door = new Door(dungeon, x, y, 1);
            onLoad(door);
            entity = door;
            break; 
        case "key":
            Key key = new Key(dungeon, x, y, 1);
            onLoad(key);
            entity = key;
            break;
        case "shelter":
            Shelter shelter = new Shelter(dungeon, x, y);
            onLoad(shelter);
            entity = shelter;
            break; 
        case "bomb":
            HiddenBomb bomb = new HiddenBomb(dungeon, x, y);
            onLoad(bomb);
            entity = bomb;
            break;     
        }
        dungeon.addEntity(entity);
    }

    /**
     * Recursively load the goals making use of the given json file
     * @param json - current json object
     * @param allGoals - GoalGroup to be added to
     * @return - new GoalGroup after changes
     */
    private GoalGroup loadGoals(JSONObject json, GoalGroup allGoals) {
        String type = json.getString("goal");
        // If goal is of type OR or AND - this must become a group of goals
        if (type.equals("OR") || type.equals("AND")) {
            // Add subgoals to a new goal group - then add this goal group to allGoals
            // This builds up a logical tree of goals. With groups representing AND/OR
            GoalGroup goalGroup = new GoalGroup(type);
            JSONArray subgoals = json.getJSONArray("subgoals");
            for (int i = 0; i < subgoals.length(); i++) {
                goalGroup = loadGoals(subgoals.getJSONObject(i), goalGroup);
            }
            allGoals.addGoal(goalGroup);
        } else {
            // add normal goal to the group of goals
            allGoals.addGoal(type);
        }
        return allGoals;
    }

    public abstract void onLoad(Entity player);

    public abstract void onLoad(Wall wall);

    public abstract void onLoad(Exit exit);

    public abstract void onLoad(Boulder boulder);

    public abstract void onLoad(Switch switchObject);

    public abstract void onLoad(Potion potion);

    public abstract void onLoad(Treasure treasure);

    public abstract void onLoad(Portal portal);

    public abstract void onLoad(Weapon weapon, int type);

    public abstract void onLoad(Enemy enemy, int type);

    public abstract void onLoad(Door door);

    public abstract void onLoad(Key key);

    public abstract void onLoad(Shelter shelter);

    public abstract void onLoad(HiddenBomb bomb);
}
