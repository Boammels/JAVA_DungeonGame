package test;

    //TODO import junit

import unsw.dungeon.Boulder;
import unsw.dungeon.Door;
import unsw.dungeon.Dungeon;
import unsw.dungeon.Enemy;
import unsw.dungeon.Exit;
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
        TestDungeonLoader load = new TestDungeonLoader("movementTest.json");
        Dungeon dungeon = load.load();
        Player player = dungeon.getPlayer();

        player.moveRight();
        assert();
        player.moveRight();
        player.moveLeft();
        player.moveLeft();
        player.moveUp();
        player.moveUp();
        player.moveDown();
        player.moveDown();


        




    }
}