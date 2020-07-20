package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;


import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import unsw.dungeon.DungeonController;
import unsw.dungeon.Boulder;
import unsw.dungeon.Door;
import unsw.dungeon.Dungeon;
import unsw.dungeon.DungeonControllerLoader;
import unsw.dungeon.DungeonLoader;
import unsw.dungeon.Enemy;
import unsw.dungeon.Entity;
import unsw.dungeon.Exit;
import unsw.dungeon.Goal;
import unsw.dungeon.Player;
import unsw.dungeon.Portal;
import unsw.dungeon.Potion;
import unsw.dungeon.Switch;
import unsw.dungeon.Treasure;
import unsw.dungeon.Wall;
import unsw.dungeon.Weapon;
import unsw.dungeon.Key;

public class TestJunit1 {

    @Test
    public void playerMove() throws IOException {
        String currentDirectory = System.getProperty("user.dir");
        System.out.println(currentDirectory);
        TestDungeonLoader load = new TestDungeonLoader("dungeons/maze.json");
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
    public void testTools() throws IOException {
        String currentDirectory = System.getProperty("user.dir");
        System.out.println(currentDirectory);
        TestDungeonLoader load = new TestDungeonLoader(currentDirectory + "/bin/test/keyDoorTreasurePortal.json");
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
}