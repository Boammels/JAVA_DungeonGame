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
}