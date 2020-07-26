package unsw.dungeon;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DungeonScreen1 {

    private Stage stage;
    private String title;
    private Scene scene;

    public DungeonScreen1(Stage stage) throws IOException {
        // stage.setTitle("Dungeon");
        this.stage = stage;
        title = "Dungeon";

        DungeonControllerLoader dungeonLoader = new DungeonControllerLoader("boulders.json");

        DungeonController controller = dungeonLoader.loadController();

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
    }

}