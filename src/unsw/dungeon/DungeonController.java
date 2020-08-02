package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.File;

/**
 * A JavaFX controller for the dungeon.
 * @author Robert Clifton-Everest
 *
 */
public class DungeonController {

    @FXML
    private GridPane squares;

    private List<ImageView> initialEntities;

    private Player player;

    private Dungeon dungeon;

    private Screen nextDungeon;
    private GameOverScreen gameOverScreen;
    private DungeonScreen currentDungeon;

    public DungeonController(Dungeon dungeon, List<ImageView> initialEntities, Screen nextDungeon) {
        this.dungeon = dungeon;
        this.player = dungeon.getPlayer();
        this.initialEntities = new ArrayList<>(initialEntities);
        this.nextDungeon = nextDungeon;
        dungeon.setController(this);
    }

    @FXML
    public void initialize() {
        Image ground = new Image((new File("images/dirt_0_new.png")).toURI().toString());

        // Add the ground first so it is below all other entities
        for (int x = 0; x < dungeon.getWidth(); x++) {
            for (int y = 0; y < dungeon.getHeight(); y++) {
                squares.add(new ImageView(ground), x, y);
            }
        }

        for (ImageView entity : initialEntities)
            squares.getChildren().add(entity);

    }

    @FXML
    public void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
        case UP:
            player.moveUp();
            break;
        case DOWN:
            player.moveDown();
            break;
        case LEFT:
            player.moveLeft();
            break;
        case RIGHT:
            player.moveRight();
            break;
        case ESCAPE:
            dungeon.start();
            break;
        default:
            break;
        }
        handleStateChange();
    }

    public void handleStateChange() {
        if (dungeon.getState().equals("DungeonComplete")) {
            if (nextDungeon != null) {
                nextDungeon.start();
            }
        } else if (dungeon.getState().equals("PlayerDead")) {
            gameOverScreen.getController().setDungeonScreen(currentDungeon);
            gameOverScreen.start();
        }
    }

    public void startDungeon() {
        // dungeon.setState(dungeon.getDungeonInProgressState());
        dungeon.start();
    }

    public void setGameOverScreen(GameOverScreen gameOver) {
        this.gameOverScreen = gameOver;
    }

    public void setDungeonScreen(DungeonScreen dungeon) {
        this.currentDungeon = dungeon;
    }

    public void setNextDungeon(Screen dungeon) {
        this.nextDungeon = dungeon;
    }
}

