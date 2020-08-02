package unsw.dungeon;

import java.io.IOException;
import java.util.List;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LevelSelectScreen implements Screen {
    private Stage stage;
    private String title;
    private LevelSelectController controller;
    private Scene scene;

    public LevelSelectScreen(Stage stage, MainMenuScreen mainMenu, List<DungeonScreen> dungeonScreens) throws IOException {
        this.stage = stage;
        title = "Level Select Screen";

        controller = new LevelSelectController();
        controller.setDungeons(mainMenu, dungeonScreens);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LevelSelectView.fxml"));
        loader.setController(controller);

        // load into a Parent node called root
        Parent root = loader.load();
        scene = new Scene(root, 500, 300);
    }

    public void start() {
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    public LevelSelectController getController() {
        return controller;
    }
}