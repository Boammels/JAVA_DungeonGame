package unsw.dungeon;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainMenuScreen implements Screen {
    private Stage stage;
    private String title;
    private MainMenuController controller;
    private Scene scene;

    public MainMenuScreen(Stage stage, DungeonScreen dungeonScreen1) throws IOException {
        this.stage = stage;
        title = "Start Screen";

        controller = new MainMenuController();
        controller.setDungeonScreen(dungeonScreen1);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenuView.fxml"));
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

    public MainMenuController getController() {
        return controller;
    }

}