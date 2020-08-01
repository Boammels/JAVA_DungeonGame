package unsw.dungeon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DungeonApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        DungeonScreen dungeonScreen5 = new DungeonScreen(primaryStage, "keyDoorTreasurePortal.json", null);
        DungeonScreen dungeonScreen4 = new DungeonScreen(primaryStage, "complexGoal3.json", dungeonScreen5);
        DungeonScreen dungeonScreen3 = new DungeonScreen(primaryStage, "advanced.json", dungeonScreen4);
        DungeonScreen dungeonScreen2 = new DungeonScreen(primaryStage, "boulders.json", dungeonScreen3);
        DungeonScreen dungeonScreen1 = new DungeonScreen(primaryStage, "maze.json", dungeonScreen2);
        List<DungeonScreen> allDungeons = new ArrayList<>();
        allDungeons.add(dungeonScreen1);
        allDungeons.add(dungeonScreen2);
        allDungeons.add(dungeonScreen3);
        allDungeons.add(dungeonScreen4);
        allDungeons.add(dungeonScreen5);
        MainMenuScreen mainScreen = new MainMenuScreen(primaryStage, dungeonScreen1);
        GameOverScreen gameOver = new GameOverScreen(primaryStage, mainScreen);
        for (DungeonScreen d : allDungeons) {
            d.getController().setGameOverScreen(gameOver);
            d.getController().setDungeonScreen(d);
        }
        LevelSelectScreen levelSelectScreen = new LevelSelectScreen(primaryStage, mainScreen, allDungeons);
        mainScreen.getController().setLevelSelectScreen(levelSelectScreen);

        mainScreen.start();

        // primaryStage.setTitle("Dungeon");

        // DungeonControllerLoader dungeonLoader = new DungeonControllerLoader("boulders.json");

        // DungeonController controller = dungeonLoader.loadController();

        // FXMLLoader loader = new FXMLLoader(getClass().getResource("DungeonView.fxml"));
        // loader.setController(controller);
        // Parent root = loader.load();
        // Scene scene = new Scene(root);
        // root.requestFocus();
        // primaryStage.setScene(scene);
        // primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
