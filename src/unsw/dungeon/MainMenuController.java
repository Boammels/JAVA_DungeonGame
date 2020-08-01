package unsw.dungeon;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainMenuController {
    
    private DungeonScreen dungeonScreen;
    private LevelSelectScreen levelSelectScreen;

    @FXML
    private Button play;

    @FXML
    private Button levelSelect;

    public void setDungeonScreen(DungeonScreen dungeonScreen) {
        this.dungeonScreen = dungeonScreen;
    }

    public void setLevelSelectScreen(LevelSelectScreen levelSelectScreen) {
        this.levelSelectScreen = levelSelectScreen;
    }

    @FXML
    public void playGame(ActionEvent event) {
        dungeonScreen.start();
    }

    @FXML
    public void gotoLevelSelect(ActionEvent event) {
        levelSelectScreen.start();
    }

}