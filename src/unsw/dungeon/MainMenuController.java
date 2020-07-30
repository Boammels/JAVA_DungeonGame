package unsw.dungeon;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainMenuController {
    
    private DungeonScreen dungeonScreen;

    @FXML
    private Button play;

    public void setDungeonScreen(DungeonScreen dungeonScreen) {
        this.dungeonScreen = dungeonScreen;
    }

    @FXML
    public void playGame(ActionEvent event) {
        dungeonScreen.start();
    }

}