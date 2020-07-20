package test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import unsw.dungeon.DungeonController;
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
    public void playerMove() throws IOException {
        String currentDirectory = System.getProperty("user.dir");
        System.out.println(currentDirectory);
        TestDungeonLoader load = new TestDungeonLoader(currentDirectory + "/bin/test/maze.json");
        Dungeon dungeon = load.load();
        System.out.println(dungeon.getEntities().size());
        Player player = dungeon.getPlayer();
        System.out.println(player);

        player.moveRight();
        // assert();
        player.moveRight();
        player.moveLeft();
        player.moveLeft();
        player.moveUp();
        player.moveUp();
        player.moveDown();
        player.moveDown();


        




    }
}