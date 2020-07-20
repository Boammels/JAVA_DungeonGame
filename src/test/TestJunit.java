package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;
import java.util.Timer;
import java.util.TimerTask;

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

public class TestJunit {

    @Test
    public void playerMove() {

        Dungeon dungeon = new Dungeon(5, 5);
        Player player = new Player(dungeon, 1, 2);
        Goal goal = new Goal("treasure");
        dungeon.addEntity(player);
        dungeon.addGoal(goal);
        for (int i = 0; i < 4; i++) {
            Wall wall = new Wall(0, i);
            Wall wall2 = new Wall(3, i);
            dungeon.addEntity(wall);
            dungeon.addEntity(wall2);
        }
        for (int i = 1; i < 3; i++) {
            Wall wall = new Wall(i, 0);
            Wall wall2 = new Wall(i, 3);
            dungeon.addEntity(wall);
            dungeon.addEntity(wall2);
        }
        Treasure treasure = new Treasure(dungeon, 4, 4);
        dungeon.addEntity(treasure);

        // remove above if below works
        /**
         * TestDungeonLoader load = new TestDungeonLoader("movementTest.json"); Dungeon
         * dungeon = load.load(); Player player = dungeon.getPlayer();
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

    @Test
    public void KeyDoorPortalTreasure() {
        Dungeon dungeon = new Dungeon(7, 4);
        Goal goal = new Goal("treasure");
        dungeon.addGoal(goal);
        for (int i = 0; i < 7; i++) {
            Wall wall = new Wall(i, 0);
            Wall wall2 = new Wall(i, 3);
            dungeon.addEntity(wall);
            dungeon.addEntity(wall2);
        }
        for (int i = 1; i < 3; i++) {
            Wall wall = new Wall(0, i);
            Wall wall2 = new Wall(6, i);
            dungeon.addEntity(wall);
            dungeon.addEntity(wall2);
        }
        Player player = new Player(dungeon, 1, 1);
        Key key = new Key(dungeon, 1, 2, 1);
        Door door = new Door(dungeon, 2, 1, 1);
        dungeon.addEntity(player);
        dungeon.addEntity(key);
        dungeon.addEntity(door);
        Portal portal1 = new Portal(dungeon, 3, 2);
        dungeon.addEntity(portal1);
        Portal portal2 = new Portal(dungeon, 5, 1);
        dungeon.addEntity(portal2);

        Treasure treasure = new Treasure(dungeon, 5, 2);
        dungeon.addEntity(treasure);
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
        assert (goal.getGoalComplete());
    }

    @Test
    public void EnemyTest1() throws InterruptedException {
        Dungeon dungeon = new Dungeon(3, 1);
        Goal goal = new Goal("enemy");
        dungeon.addGoal(goal);

        Player player = new Player(dungeon, 0, 0);
        Enemy enemy = new Enemy(dungeon, player, 2, 0);
        dungeon.addEntity(player);
        dungeon.addEntity(enemy);
        Timer timer = new Timer();
        Thread.sleep(3000);
        assertEquals(dungeon.getState(), "PlayerDead");
        /*assertEquals(enemy.getX(), 1);
        assertEquals(enemy.getY(), 0);
        
        timer.schedule(new TimerTask(){
            @Override
            public void run() {
                assertEquals(enemy.getX(), 1);
                assertEquals(enemy.getY(), 0);
                
                timer.cancel();
            }
        },2000);


        */
    }

}