package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import unsw.dungeon.Door;
import unsw.dungeon.Dungeon;
import unsw.dungeon.Entity;
import unsw.dungeon.Goal;
import unsw.dungeon.Player;
import unsw.dungeon.Portal;
import unsw.dungeon.Treasure;
import unsw.dungeon.Key;

public class PortalTreasureKeyDoorTest {
    @Test
    public void testTools() throws IOException {
        String currentDirectory = System.getProperty("user.dir");
        System.out.println(currentDirectory);
        TestDungeonLoader load = new TestDungeonLoader("dungeons/keyDoorTreasurePortal.json");
        Dungeon dungeon = load.load();
        System.out.println(dungeon.getEntities().size());
        Player player = dungeon.getPlayer();
        System.out.println(player);

        Key key = null;
        Door door = null;
        Treasure treasure = null;
        for (Entity e : dungeon.getEntities()) {
            if(e instanceof Key) {
                key = (Key)e;
            } else if (e instanceof Door) {
                door = (Door)e;
            } else if (e instanceof Treasure) {
                treasure = (Treasure)e;
            }
        }
        assertEquals(player.getX(), 1);
        assertEquals(player.getY(), 1);
        player.moveRight();
        assertEquals(player.getX(), 1);
        assertEquals(player.getY(), 1);
        player.moveDown();
        assertEquals(player.getX(), 1);
        assertEquals(player.getY(), 2);
        assert (!dungeon.getEntities().contains(key));
        assertEquals(player.getKey(), 1);
        player.moveUp();
        player.moveRight();
        assert (door.isOpen());
        assertEquals(player.getX(), 2);
        assertEquals(player.getY(), 1);
        player.moveRight();
        player.moveDown();
        assertEquals(player.getX(), 5);
        assertEquals(player.getY(), 1);
        player.moveDown();
        assertEquals(player.getX(), 5);
        assertEquals(player.getY(), 2);
        assert (!dungeon.getEntities().contains(treasure));
    }

    
    @Test
    public void singlePortalTest() {
        Dungeon dungeon = new Dungeon(3,3);
        Player player = new Player(dungeon, 0, 1);
        Portal portal = new Portal(dungeon, 0, 2);
        Treasure treasure = new Treasure(dungeon, 2, 2);
        dungeon.addEntity(player);
        dungeon.addEntity(portal);
        dungeon.addEntity(treasure);
        Goal goal = new Goal("treasure");
        dungeon.addGoal(goal);

        assertEquals(portal.getExit(), null);
        player.moveDown();
        assertEquals(player.getX(), 0);
        assertEquals(player.getY(), 2);
    }  
    
    @Test
    public void doubleKey() {
        Dungeon dungeon = new Dungeon(3,3);
        Player player = new Player(dungeon, 1, 1);
        Key key1 = new Key(dungeon, 0,1,1);
        Key key2 = new Key(dungeon,2,1,2);
        Treasure treasure = new Treasure(dungeon, 2, 2);
        dungeon.addEntity(player);
        dungeon.addEntity(key1);
        dungeon.addEntity(key2);
        dungeon.addEntity(treasure);
        Goal goal = new Goal("treasure");
        dungeon.addGoal(goal);
        player.moveLeft();
        assertEquals(player.getKey(),1);
        assert(!dungeon.getEntities().contains(key1));
        player.moveRight();
        player.moveRight();
        assertEquals(player.getKey(),1);
        assert(dungeon.getEntities().contains(key2));
    }
    
}