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

public class GoalTest {
    @Test
    public void testExitANDBoulder() throws IOException {
        TestDungeonLoader load = new TestDungeonLoader("dungeons/complexGoal1.json");
        Dungeon dungeon = load.load();
        Player player = dungeon.getPlayer();

        player.moveDown();
        assertEquals(player.getX(), 1);
        assertEquals(player.getY(), 2);
        assertEquals(dungeon.getState(), "GameInProgress");
        player.moveRight();
        assertEquals(player.getX(), 2);
        assertEquals(player.getY(), 2);
        assertEquals(dungeon.getState(), "GameInProgress");
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
        assertEquals(dungeon.getState(), "GameInProgress");
        player.moveUp();
        player.moveRight();
        player.moveUp();
        player.moveUp();
        // Boulders and exit with exit last means dungeon complete
        assertEquals(dungeon.getState(), "DungeonComplete");
    }

    @Test
    public void testExitAndTreasure() throws IOException {
        TestDungeonLoader load = new TestDungeonLoader("dungeons/complexGoal1.json");
        Dungeon dungeon = load.load();
        Player player = dungeon.getPlayer();

        player.moveRight();
        player.moveRight();
        // We pick up the treasure, not enough to complete the dungeon
        assertEquals(dungeon.getState(), "GameInProgress");
        player.moveRight();
        
        // Treasure and exit with exit last means dungeon complete
        assertEquals(dungeon.getState(), "DungeonComplete");
    }

    @Test
    public void testTreasureAndBoulder() throws IOException {
        TestDungeonLoader load = new TestDungeonLoader("dungeons/complexGoal1.json");
        Dungeon dungeon = load.load();
        Player player = dungeon.getPlayer();
        player.moveDown();
        assertEquals(player.getX(), 1);
        assertEquals(player.getY(), 2);
        assertEquals(dungeon.getState(), "GameInProgress");
        player.moveRight();
        assertEquals(player.getX(), 2);
        assertEquals(player.getY(), 2);
        assertEquals(dungeon.getState(), "GameInProgress");
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
        assertEquals(dungeon.getState(), "GameInProgress");
        player.moveLeft();
        player.moveLeft();
        player.moveLeft();
        assertEquals(player.getX(), 1);
        assertEquals(player.getY(), 4);
        player.moveUp();
        player.moveUp();
        player.moveUp();
        assertEquals(player.getX(), 1);
        assertEquals(player.getY(), 1);
        player.moveRight();
        player.moveRight();
        // Treasure and boulders collected, however require exit and (treasure or boulders)
        // so game should remain in progress
        assertEquals(dungeon.getState(), "GameInProgress");
        player.moveRight();
        // All goals complete means dungeon complete
        assertEquals(dungeon.getState(), "DungeonComplete");
    }

    @Test
    public void testExitFirstThenTreasure() throws IOException {
        TestDungeonLoader load = new TestDungeonLoader("dungeons/complexGoal1.json");
        Dungeon dungeon = load.load();
        Player player = dungeon.getPlayer();
        player.moveDown();
        player.moveDown();
        player.moveDown();
        player.moveRight();
        player.moveRight();
        player.moveRight();
        player.moveUp();
        player.moveUp();
        player.moveUp();
        assertEquals(dungeon.getState(), "GameInProgress");
        player.moveLeft();
        // pick up treasure, but exit must be completed last - so dungeon still not complete
        assertEquals(dungeon.getState(), "GameInProgress");
        player.moveRight();
        // As exit is last, this completes the dungeon
        assertEquals(dungeon.getState(), "DungeonComplete");
    }

    @Test
    public void RedundantGoal() throws IOException {
        TestDungeonLoader load = new TestDungeonLoader("dungeons/complexGoal2.json");
        Dungeon dungeon = load.load();
        Player player = dungeon.getPlayer();

        player.moveDown();
        assertEquals(player.getX(), 1);
        assertEquals(player.getY(), 2);
        assertEquals(dungeon.getState(), "GameInProgress");
        player.moveRight();
        assertEquals(player.getX(), 2);
        assertEquals(player.getY(), 2);
        assertEquals(dungeon.getState(), "GameInProgress");
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
        assertEquals(dungeon.getState(), "GameInProgress");
        player.moveUp();
        player.moveRight();
        player.moveUp();
        player.moveUp();
        // Only boulders and exit, game not complete
        assertEquals(dungeon.getState(), "GameInProgress");
        player.moveLeft();
        // Exit must come last
        assertEquals(dungeon.getState(), "GameInProgress");
        player.moveRight();
        assertEquals(dungeon.getState(), "DungeonComplete");
    }
}