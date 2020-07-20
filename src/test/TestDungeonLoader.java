package test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import unsw.dungeon.Boulder;
import unsw.dungeon.Door;
import unsw.dungeon.Dungeon;
import unsw.dungeon.Enemy;
import unsw.dungeon.Exit;
import unsw.dungeon.Goal;
import unsw.dungeon.GoalGroup;
import unsw.dungeon.Player;
import unsw.dungeon.Portal;
import unsw.dungeon.Potion;
import unsw.dungeon.Switch;
import unsw.dungeon.Treasure;
import unsw.dungeon.Wall;
import unsw.dungeon.Weapon;
import unsw.dungeon.Key;
import unsw.dungeon.GoalComponent;
import unsw.dungeon.Goal;
import unsw.dungeon.GoalGroup;

public class TestDungeonLoader {

    private JSONObject json;

    public TestDungeonLoader(String filename) throws FileNotFoundException {
        // String canonicalPath = "";
        // try {
        //     canonicalPath = new File(".").getCanonicalPath();
        // } catch (Exception e) {
        //     System.out.println("cadsionc");
        // }
        // System.out.println(canonicalPath);
        json = new JSONObject(new JSONTokener(new FileReader(filename)));
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
        // Must do an initial check on all switches to turn them on if boulders are on them.
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

        switch (type) {
        case "player":
            Player player = new Player(dungeon, x, y);
            dungeon.setPlayer(player);
            dungeon.addEntity(player);
            break;
        case "wall":
            Wall wall = new Wall(x, y);
            dungeon.addEntity(wall);
            break;
        case "exit":
            Exit exit = new Exit(dungeon, x, y);
            dungeon.addEntity(exit);
            break;
        case "boulder":
            Boulder boulder = new Boulder(dungeon, x, y);
            dungeon.addEntity(boulder);
            break;
        case "switch":
            Switch switchObject = new Switch(dungeon, x, y);
            dungeon.addEntity(switchObject);
            break;
        case "invincibility":
            Potion potion = new Potion(dungeon, x, y);
            dungeon.addEntity(potion);
            break;
        case "treasure":
            Treasure treasure = new Treasure(dungeon, x, y);
            dungeon.addEntity(treasure);
            break;
        case "portal":
            Portal portal = new Portal(dungeon, x, y);
            dungeon.addEntity(portal);
            break;
        case "sword":
            Weapon weapon = new Weapon(dungeon, x, y);
            dungeon.addEntity(weapon);
            break;
        case "enemy":
            Enemy enemy = new Enemy(dungeon, dungeon.getPlayer(), x, y);
            dungeon.addEntity(enemy);
            break;
        case "door":
            Door door = new Door(dungeon, x, y, 1);
            dungeon.addEntity(door);
            break; 
        case "key":
            Key key = new Key(dungeon, x, y, 1);
            dungeon.addEntity(key);
            break;    
        }
    }
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
}