package unsw.dungeon;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class GameOverController {
    private DungeonScreen dungeonScreen;
    private MainMenuScreen mainMenuScreen;

    @FXML
    private Button restart;

    @FXML
    private Button mainMenu;

    public void setDungeonScreen(DungeonScreen dungeonScreen) {
        this.dungeonScreen = dungeonScreen;
    }

    public void setMainMenuScreen(MainMenuScreen mainMenuScreen) {
        this.mainMenuScreen = mainMenuScreen;
    }

    @FXML
    public void restart(ActionEvent event) {
        dungeonScreen.start();
    }

    @FXML
    public void gotoMainMenu(ActionEvent event) {
        mainMenuScreen.start();
    }
}