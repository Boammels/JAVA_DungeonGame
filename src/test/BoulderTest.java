package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import unsw.dungeon.Boulder;
import unsw.dungeon.Dungeon;
import unsw.dungeon.Goal;
import unsw.dungeon.Player;
import unsw.dungeon.Portal;
import unsw.dungeon.Switch;
import unsw.dungeon.Wall;


public class BoulderTest {
    
    @Test
    public void testWalls() throws IOException {
        TestDungeonLoader load = new TestDungeonLoader("dungeons/simpleBoulders.json");
        Dungeon dungeon = load.load();
        Player player = dungeon.getPlayer();

        player.moveDown();
        assertEquals(player.getX(), 1);
        assertEquals(player.getY(), 2);
        assertEquals(dungeon.getState(), "GameInProgress");
        player.moveRight();
        assertEquals(player.getX(), 2);
        assertEquals(player.getY(), 2);
        player.moveDown();
        assertEquals(player.getX(), 2);
        assertEquals(player.getY(), 3);
        player.moveDown();
        assertEquals(player.getX(), 2);
        assertEquals(player.getY(), 3);
        player.moveDown();
        assertEquals(player.getX(), 2);
        assertEquals(player.getY(), 3);
        player.moveLeft();
        player.moveDown();
        player.moveRight();
        assertEquals(dungeon.getState(), "GameInProgress");
        player.moveRight();
        player.moveRight();
        assertEquals(dungeon.getState(), "DungeonComplete");
    }

    @Test
    public void testBoulderOnBoulder() throws IOException {
        TestDungeonLoader load = new TestDungeonLoader("dungeons/simpleBoulders.json");
        Dungeon dungeon = load.load();
        Player player = dungeon.getPlayer();

        player.moveRight();
        assertEquals(player.getX(), 2);
        assertEquals(player.getY(), 1);
        // Moving down onto a boulder, boulder should not move, meaning player should not move
        player.moveDown();
        assertEquals(player.getX(), 2);
        assertEquals(player.getY(), 1);
    }

    @Test
    public void testPushIntoWalls() throws IOException {
        TestDungeonLoader load = new TestDungeonLoader("dungeons/simpleBoulders.json");
        Dungeon dungeon = load.load();
        Player player = dungeon.getPlayer();

        player.moveRight();
        player.moveRight();
        player.moveDown();
        player.moveLeft();
        player.moveLeft();
        player.moveLeft();
        assertEquals(player.getX(), 2);
        assertEquals(player.getY(), 2);
        player.moveDown();
        player.moveDown();
        assertEquals(player.getX(), 2);
        assertEquals(player.getY(), 3);
        player.moveLeft();
        player.moveUp();
        player.moveUp();
        player.moveUp();
        assertEquals(player.getX(), 1);
        assertEquals(player.getY(), 2);
    }

    @Test
    public void testPoulderThroughPortal() {
        Dungeon dungeon = new Dungeon(5,3);
        Player player = new Player(dungeon, 2, 0);
        Boulder boulder = new Boulder(dungeon, 2, 1);
        Portal portal1 = new Portal(dungeon, 2, 2);
        Switch sw = new Switch(dungeon, 3, 4);
        dungeon.addEntity(sw);
        dungeon.addEntity(player); 
        dungeon.addEntity(boulder);
        dungeon.addEntity(portal1);
        Portal portal2 = new Portal(dungeon, 2, 3);
        dungeon.addEntity(portal2);
        Goal goal = new Goal("boulders");
        dungeon.addGoal(goal);
        Wall wall = new Wall(2,4);
        dungeon.addEntity(wall);
        player.moveDown();
        assertEquals(boulder.getX(),2);
        assertEquals(boulder.getY(),1);
        dungeon.getEntities().remove(wall);
        player.moveDown();
        assertEquals(boulder.getX(),2);
        assertEquals(boulder.getY(),4);
    }
}