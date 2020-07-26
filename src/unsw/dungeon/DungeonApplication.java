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
        DungeonScreen dungeonScreen3 = new DungeonScreen(primaryStage, "advanced.json", null);
        DungeonScreen dungeonScreen2 = new DungeonScreen(primaryStage, "boulders.json", dungeonScreen3);
        DungeonScreen dungeonScreen1 = new DungeonScreen(primaryStage, "maze.json", dungeonScreen2);
        MainMenuScreen mainScreen = new MainMenuScreen(primaryStage, dungeonScreen1);

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
