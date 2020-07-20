package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

import unsw.dungeon.Boulder;
import unsw.dungeon.Door;
import unsw.dungeon.Dungeon;
import unsw.dungeon.DungeonControllerLoader;
import unsw.dungeon.DungeonLoader;
import unsw.dungeon.Enemy;
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
    public void playerMove() {

        Dungeon dungeon = new Dungeon(5,5);
        Player player = new Player(dungeon, 1,2);
        Goal goal = new Goal("treasure");
        dungeon.addEntity(player);
        dungeon.addGoal(goal);
        for(int i=0; i < 4; i++) {
            Wall wall = new Wall(0,i);
            Wall wall2 = new Wall (3,i);
            dungeon.addEntity(wall);
            dungeon.addEntity(wall2);
        }
        for(int i=1;i<3;i++) {
            Wall wall = new Wall(i,0);
            Wall wall2 = new Wall (i,3);
            dungeon.addEntity(wall);
            dungeon.addEntity(wall2);
        }
        Treasure treasure = new Treasure(dungeon, 4,4);
        dungeon.addEntity(treasure);

        //remove above if it works
        /**
         * 
         */
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
}