package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import unsw.dungeon.Dungeon;
import unsw.dungeon.Goal;
import unsw.dungeon.Player;

import unsw.dungeon.Treasure;


public class TestJunit1 {

    @Test
    public void playerMove() throws IOException {
        String currentDirectory = System.getProperty("user.dir");
        System.out.println(currentDirectory);
        TestDungeonLoader load = new TestDungeonLoader("dungeons/movementTest.json");
        Dungeon dungeon = load.load();
        System.out.println(dungeon.getEntities().size());
        Player player = dungeon.getPlayer();
        System.out.println(player);

        assertEquals(player.getX(), 1);
        assertEquals(player.getY(), 2);
        player.moveRight();
        assertEquals(player.getX(), 2);
        assertEquals(player.getY(), 2);
        player.moveRight();
        assertEquals(player.getX(), 2);
        assertEquals(player.getY(), 2);
        player.moveLeft();
        assertEquals(player.getX(), 1);
        assertEquals(player.getY(), 2);
        player.moveLeft();
        assertEquals(player.getX(), 1);
        assertEquals(player.getY(), 2);
        player.moveUp();
        assertEquals(player.getX(), 1);
        assertEquals(player.getY(), 1);
        player.moveUp();
        assertEquals(player.getX(), 1);
        assertEquals(player.getY(), 1);
        player.moveDown();
        assertEquals(player.getX(), 1);
        assertEquals(player.getY(), 2);
        player.moveDown();
        assertEquals(player.getX(), 1);
        assertEquals(player.getY(), 2);
    }
    
    @Test
    public void whenGameFinished() throws FileNotFoundException {
        String currentDirectory = System.getProperty("user.dir");
        System.out.println(currentDirectory);
        TestDungeonLoader load = new TestDungeonLoader("dungeons/maze.json");
        Dungeon dungeon = load.load();
        System.out.println(dungeon.getEntities().size());
        Player player = dungeon.getPlayer();
        System.out.println(player);

        player.getDungeon().completeGoal("exit");
        player.moveLeft();
        player.moveRight();
        player.moveUp();
        player.moveDown();
        assertEquals(player.getX(), 1);
        assertEquals(player.getY(), 1);
    }

    @Test
    public void verySmallMap() {
        Dungeon dungeon = new Dungeon(2,2);
        Player player = new Player(dungeon,0,0);
        dungeon.addEntity(player);
        Treasure treasure = new Treasure(dungeon,1,1);
        dungeon.addEntity(treasure);
        Goal goal = new Goal("treasure");
        dungeon.addGoal(goal);
        player.moveLeft();
        player.moveUp();
        assertEquals(player.getX(), 0);
        assertEquals(player.getY(), 0);
        player.moveDown();
        player.moveDown();
        assertEquals(player.getX(), 0);
        assertEquals(player.getY(), 1);
        player.moveUp();
        player.moveRight();
        player.moveRight();
        assertEquals(player.getX(), 1);
        assertEquals(player.getY(), 0);
    }

    
}