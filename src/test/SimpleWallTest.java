package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.Player;


public class SimpleWallTest {
    
    @Test
    public void testWalls() throws IOException {
        // String currentDirectory = System.getProperty("user.dir");
        // System.out.println(currentDirectory);
        TestDungeonLoader load = new TestDungeonLoader("dungeons/maze.json");
        Dungeon dungeon = load.load();
        // System.out.println(dungeon.getEntities().size());
        Player player = dungeon.getPlayer();
        // System.out.println(player);

        assertEquals(player.getX(), 1);
        assertEquals(player.getY(), 1);
        player.moveRight();
        assertEquals(player.getX(), 1);
        assertEquals(player.getY(), 1);
        player.moveLeft();
        assertEquals(player.getX(), 1);
        assertEquals(player.getY(), 1);
        player.moveUp();
        assertEquals(player.getX(), 1);
        assertEquals(player.getY(), 1);
        player.moveDown();
        assertEquals(player.getX(), 1);
        assertEquals(player.getY(), 2);
    }
}