package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.io.IOException;


import unsw.dungeon.Door;
import unsw.dungeon.Dungeon;
import unsw.dungeon.Enemy;
import unsw.dungeon.Entity;
import unsw.dungeon.Goal;
import unsw.dungeon.Player;
import unsw.dungeon.Portal;
import unsw.dungeon.Potion;
import unsw.dungeon.Wall;
import unsw.dungeon.Weapon;


public class EnemyWeaponPotionTest {
    @Test
    public void enemyTestKilled() {
        Dungeon dungeon = new Dungeon(3,3);
        Player player = new Player(dungeon, 0, 1);
        Enemy enemy = new Enemy(dungeon, player, 2, 1);
        Goal goal = new Goal("enemy");
        dungeon.addGoal(goal);
        dungeon.addEntity(player);
        dungeon.addEntity(enemy);
        enemy.move();
        assertEquals(enemy.getX(), 1);
        assertEquals(enemy.getY(), 1);
        enemy.move();
        assertEquals(enemy.getX(), 0);
        assertEquals(enemy.getY(), 1);
        assertEquals(dungeon.getState(), "PlayerDead");
    } 

    @Test
    public void enemyTestKilling() throws IOException {
        String currentDirectory = System.getProperty("user.dir");
        System.out.println(currentDirectory);
        TestDungeonLoader load = new TestDungeonLoader("dungeons/enemyWeaponPotion.json");
        Dungeon dungeon = load.load();

        Enemy enemy = null;
        Potion potion = null;
        Weapon weapon = null;
        Player player = dungeon.getPlayer();
        for (Entity e : dungeon.getEntities()) {
            if(e instanceof Enemy) {
                enemy = (Enemy)e;
            } else if (e instanceof Potion) {
                potion = (Potion)e;
            } else if (e instanceof Weapon) {
                weapon = (Weapon)e;
            }
        }
        assertEquals(player.getX(), 1);
        assertEquals(player.getY(), 4);
        player.moveUp();
        assertEquals(player.getX(), 1);
        assertEquals(player.getY(), 3);
        assertEquals(player.getWeapon(), 5);
        assert(!dungeon.getEntities().contains(weapon));
        enemy.move();
        assertEquals(enemy.getX(), 1);
        assertEquals(enemy.getY(), 5);
        player.moveUp();
        player.moveUp();
        assertEquals(player.getX(), 1);
        assertEquals(player.getY(), 1);
        assert(player.havePotion());
        assert(!dungeon.getEntities().contains(potion));
        enemy.move();
        assertEquals(enemy.getX(), 1);
        assertEquals(enemy.getY(), 6);
        assertEquals(dungeon.getState(), "GameInProgress");
        player.moveDown();
        player.moveDown();
        player.moveDown();
        player.moveDown();
        player.moveDown();
        assertEquals(player.getWeapon(), 5);
        assertEquals(dungeon.getState(), "DungeonComplete");
    }

    @Test
    public void enemyTestKilling2() throws IOException {
        String currentDirectory = System.getProperty("user.dir");
        System.out.println(currentDirectory);
        TestDungeonLoader load = new TestDungeonLoader("dungeons/enemyWeaponPotion.json");
        Dungeon dungeon = load.load();

        Enemy enemy = null;
        Weapon weapon = null;
        Player player = dungeon.getPlayer();
        for (Entity e : dungeon.getEntities()) {
            if(e instanceof Enemy) {
                enemy = (Enemy)e;
            } else if (e instanceof Weapon) {
                weapon = (Weapon)e;
            }
        }
        assertEquals(player.getX(), 1);
        assertEquals(player.getY(), 4);
        player.moveUp();
        assertEquals(player.getX(), 1);
        assertEquals(player.getY(), 3);
        assertEquals(player.getWeapon(), 5);
        assert(!dungeon.getEntities().contains(weapon));
        enemy.move();
        assertEquals(enemy.getX(), 1);
        assertEquals(enemy.getY(), 5);
        player.moveDown();
        player.moveDown();
        assertEquals(player.getWeapon(), 4);
        assertEquals(dungeon.getState(), "DungeonComplete");
    }

    @Test
    public void enemyThroughPortal() {
        Dungeon dungeon = new Dungeon(5,5);
        Player player = new Player(dungeon, 1, 1);
        Enemy enemy = new Enemy(dungeon, player, 3, 1);
        Portal portal = new Portal(dungeon, 2, 1);
        dungeon.addEntity(portal);
        Portal portal2 = new Portal(dungeon, 3, 3);
        dungeon.addEntity(player);
        dungeon.addEntity(enemy);
        dungeon.addEntity(portal2);

        Goal goal = new Goal("enemy");
        dungeon.addGoal(goal);

        enemy.move();
        
        assertEquals(enemy.getX(),3);
        assertEquals(3,enemy.getY());
    }
    
    @Test
    public void enemyThroughPortal2() {
        Dungeon dungeon = new Dungeon(5,5);
        Player player = new Player(dungeon, 1, 1);
        Enemy enemy = new Enemy(dungeon, player, 3, 1);
        Portal portal = new Portal(dungeon, 2, 1);
        dungeon.addEntity(portal);

        dungeon.addEntity(player);
        dungeon.addEntity(enemy);


        Goal goal = new Goal("enemy");
        dungeon.addGoal(goal);

        enemy.move();
        
        assertEquals(enemy.getX(),2);
        assertEquals(1,enemy.getY());
    }

    @Test
    public void enemyRunAway() {
        Dungeon dungeon = new Dungeon(7,7);
        Player player = new Player(dungeon, 3, 3);
        Enemy enemy1 = new Enemy(dungeon, player, 2, 2);
        Enemy enemy2 = new Enemy(dungeon, player, 4, 2);
        Enemy enemy3 = new Enemy(dungeon, player, 2, 4);
        Enemy enemy4 = new Enemy(dungeon, player, 4, 4);
        Goal goal = new Goal("Enemy");
        dungeon.addGoal(goal);
        dungeon.addEntity(enemy1);  dungeon.addEntity(enemy2);
        dungeon.addEntity(enemy3);  dungeon.addEntity(enemy4);
        dungeon.addEntity(player);
        for (int i = 0; i < 7; i++) {
            Wall wall1 = new Wall(i,0); Wall wall2 = new Wall(0,6-i);
            Wall wall3 = new Wall(6,i); Wall wall4 = new Wall(6-i,6);
            dungeon.addEntity(wall1);   dungeon.addEntity(wall2);
            dungeon.addEntity(wall3);   dungeon.addEntity(wall4);
        }
        player.setPotion(true);
        enemy1.move();  enemy2.move();    enemy3.move();  enemy4.move();
        enemy1.move();  enemy2.move();    enemy3.move();  enemy4.move();
        assertEquals(enemy1.getX(),1);  assertEquals(enemy1.getY(),1);
        assertEquals(enemy4.getX(),5);  assertEquals(enemy4.getY(),5);
        assertEquals(enemy3.getX(),1);  assertEquals(enemy3.getY(),5);
        assertEquals(enemy2.getX(),5);  assertEquals(enemy2.getY(),1);
        player.moveRight();
        enemy1.move();  enemy2.move();  enemy3.move();  enemy4.move();
        player.moveLeft();
        player.moveLeft();
        enemy1.move();  enemy2.move();  enemy3.move();  enemy4.move();
        assertEquals(enemy1.getX(),1);  assertEquals(enemy1.getY(),1);
        assertEquals(enemy4.getX(),5);  assertEquals(enemy4.getY(),5);
        assertEquals(enemy3.getX(),1);  assertEquals(enemy3.getY(),5);
        assertEquals(enemy2.getX(),5);  assertEquals(enemy2.getY(),1);
        player.moveRight();
        player.moveUp();
        enemy1.move();  enemy2.move();  enemy3.move();  enemy4.move();
        player.moveDown();
        player.moveDown();
        enemy1.move();  enemy2.move();  enemy3.move();  enemy4.move();
        assertEquals(enemy1.getX(),1);  assertEquals(enemy1.getY(),1);
        assertEquals(enemy4.getX(),5);  assertEquals(enemy4.getY(),5);
        assertEquals(enemy3.getX(),1);  assertEquals(enemy3.getY(),5);
        assertEquals(enemy2.getX(),5);  assertEquals(enemy2.getY(),1);
    }

    @Test
    public void enemyAndClosedDoor() {
        Dungeon dungeon = new Dungeon(7,7);
        Player player = new Player(dungeon, 1, 3);
        Enemy enemy = new Enemy(dungeon, player, 5, 3);
        Door door = new Door(dungeon, 4, 3, 1);
        dungeon.addEntity(player);
        dungeon.addEntity(enemy);
        dungeon.addEntity(door);
        Goal goal = new Goal("enemy");
        dungeon.addGoal(goal);
        enemy.move();
        assertEquals(enemy.getX(),5);  
        assertEquals(enemy.getY(),2);
    }

    @Test
    public void enemyLogicNoBackWay() {
        Dungeon dungeon = new Dungeon(2,3);
        Player player = new Player(dungeon, 1, 0);
        Enemy enemy = new Enemy(dungeon, player, 0, 2);
        dungeon.addEntity(player);
        dungeon.addEntity(enemy);
        Goal goal = new Goal("enemy");
        dungeon.addGoal(goal);
        enemy.move();
        player.moveLeft();
        enemy.move();
        assertEquals(enemy.getX(),1);
        assertEquals(enemy.getY(),1);
    }

    @Test
    public void enemyAndOpenDoor() {
        Dungeon dungeon = new Dungeon(7,7);
        Player player = new Player(dungeon, 1, 3);
        Enemy enemy = new Enemy(dungeon, player, 5, 3);
        Door door = new Door(dungeon, 4, 3, 1);
        dungeon.addEntity(player);
        dungeon.addEntity(enemy);
        dungeon.addEntity(door);
        Goal goal = new Goal("enemy");
        dungeon.addGoal(goal);
        door.openDoor();
        enemy.move();
        assertEquals(enemy.getX(),4);  
        assertEquals(enemy.getY(),3);
    }
    
}