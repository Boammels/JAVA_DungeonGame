package unsw.dungeon;

import java.io.IOException;

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
        MainMenuScreen mainScreen = new MainMenuScreen(primaryStage, dungeonScreen1);
        LevelSelectScreen levelSelectScreen = new LevelSelectScreen(primaryStage, mainScreen, dungeonScreen1, dungeonScreen2, dungeonScreen3, dungeonScreen4, dungeonScreen5);
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
