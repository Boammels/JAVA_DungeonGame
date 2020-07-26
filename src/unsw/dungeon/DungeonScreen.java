package unsw.dungeon;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DungeonScreen {

    private Stage stage;
    private String title;
    private Scene scene;
    private DungeonController controller;

    public DungeonScreen(Stage stage, String dungeonToLoad, DungeonScreen nextDungeon) throws IOException {
        // stage.setTitle("Dungeon");
        this.stage = stage;
        title = "Dungeon";

        DungeonControllerLoader dungeonLoader = new DungeonControllerLoader(dungeonToLoad);

        DungeonController controller = dungeonLoader.loadController(nextDungeon);
        this.controller = controller;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("DungeonView.fxml"));
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        root.requestFocus();
        this.scene = scene;
        stage.setScene(scene);
        stage.show();
    }

    public void start() {
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
        controller.startDungeon();
    }

}