package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class LevelSelectController {
    private List<DungeonScreen> dungeonScreens = new ArrayList<>();
    private MainMenuScreen mainMenuScreen;

    @FXML
    private GridPane buttonGrid;

    @FXML
    private Button backToMainMenu;

    @FXML
    public void initialize() {
        // for (int i = 0; i < dungeonScreens.size(); i++) {
        int i = 1;
        for (DungeonScreen d : dungeonScreens) {
            Button node = new Button("Level " + i);
            buttonGrid.add(node, i, 0, 1, 1);
            node.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent e) {
                    d.start();
                }
            });
            i++;
            // Bind button press to a method i.e. dungeonScreen.start()
        }
    }

    public void setDungeons(MainMenuScreen mainMenuScreen, DungeonScreen ... dungeonScreens) {
        for (DungeonScreen d : dungeonScreens) {
            this.dungeonScreens.add(d);
        }
        this.mainMenuScreen = mainMenuScreen;
        // startUp();
    }

    @FXML
    public void backToMainMenu(ActionEvent event) {
        mainMenuScreen.start();
    }
}