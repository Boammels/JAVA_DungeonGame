package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.File;



/**
 * A JavaFX controller for the dungeon.
 * @author Robert Clifton-Everest
 *
 */
public class DungeonController {

    @FXML
    private GridPane squares;

    private Text gameObjectives;
    private Button retry;
    private Button mainMenu;
    private Button levelSelect;

    private List<ImageView> initialEntities;

    private Player player;

    private Dungeon dungeon;

    private Screen nextDungeon;
    private GameOverScreen gameOverScreen;
    private DungeonScreen currentDungeon;
    private MainMenuScreen mainMenuScreen;
    private LevelSelectScreen levelSelectScreen;

    //private MediaPlayer musicPlayer;
    //private AudioClip audio;

    public DungeonController(Dungeon dungeon, List<ImageView> initialEntities, Screen nextDungeon) {
        this.dungeon = dungeon;
        this.player = dungeon.getPlayer();
        this.initialEntities = new ArrayList<>(initialEntities);
        this.nextDungeon = nextDungeon;
    }

    @FXML
    public void initialize() {
        Image ground = new Image((new File("images/ground.png")).toURI().toString());

        // Add the ground first so it is below all other entities
        for (int x = 0; x < dungeon.getWidth(); x++) {
            for (int y = 0; y < dungeon.getHeight(); y++) {
                squares.add(new ImageView(ground), x, y);
            }
        }
        
        for (ImageView entity : initialEntities)
            squares.getChildren().add(entity);
        
        StateObserver stateHandler = new ControllerStateObserver(this);
        dungeon.attatchObserver(stateHandler);

        // Set up the buttons on the dungeon screen that will either restart the dungeon
        // or navigate to another screen.
        gameObjectives = new Text();
        gameObjectives.setFont(new Font("Arial", 15));
        gameObjectives.textProperty().bind(dungeon.getGoalText());
        squares.add(gameObjectives, 0, dungeon.getHeight());
        squares.setColumnSpan(gameObjectives, dungeon.getWidth());
        retry = new Button("RETRY");
        retry.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                dungeon.start();
                squares.requestFocus();
            }
        });
        squares.add(retry, 0, dungeon.getHeight() + 1);
        squares.setColumnSpan(retry, dungeon.getWidth());

        mainMenu = new Button("MAIN MENU");
        mainMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                mainMenuScreen.start();
                dungeon.stopEnemies();
                squares.requestFocus();
            }
        });
        squares.add(mainMenu, 2, dungeon.getHeight() + 1);
        squares.setColumnSpan(mainMenu, dungeon.getWidth());

        levelSelect = new Button("LEVEL SELECT");
        levelSelect.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                levelSelectScreen.start();
                dungeon.stopEnemies();
                squares.requestFocus();
            }
        });
        squares.add(levelSelect, 5, dungeon.getHeight() + 1);
        squares.setColumnSpan(levelSelect, dungeon.getWidth());

        //URL sound = getClass().getResource("music/Woosh_stutter.wav");
        //final Media media = new Media(sound.toString());
        //musicPlayer = new MediaPlayer(media);
        
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
        //String path = "./Woosh_stutter.wav";
        //Media media = new Media(new File(path).toURI().toString());  
        //MediaPlayer mediaPlayer = new MediaPlayer(media);  
        //mediaPlayer.setAutoPlay(true);  
        //audio = new AudioClip(getClass().getResource("Woosh_stutter.wav").toString());
        //audio.play();
        // handleStateChange();
    }

    // public void handleStateChange() {
    //     if (dungeon.getState().equals("DungeonComplete")) {
    //         if (nextDungeon != null) {
    //             nextDungeon.start();
    //         }
    //     } else if (dungeon.getState().equals("PlayerDead")) {
    //         gameOverScreen.getController().setDungeonScreen(currentDungeon);
    //         gameOverScreen.start();
    //     }
    // }

    /**
     * When observer tells us the dungeon state has changed, handle accordingly 
     * @param newState
     */
    public void handleStateChange(String newState) {
        if (newState.equals("DungeonComplete")) {
            if (nextDungeon != null) {
                nextDungeon.start();
            }
        } else if (newState.equals("PlayerDead")) {
            gameOverScreen.getController().setDungeonScreen(currentDungeon);
            gameOverScreen.start();
        }
    }

    public void startDungeon() {
        dungeon.start();
    }

    public void setGameOverScreen(GameOverScreen gameOver) {
        this.gameOverScreen = gameOver;
    }

    public void setMainMenuScreen(MainMenuScreen mainMenu) {
        this.mainMenuScreen = mainMenu;
    }

    public void setLevelSelectScreen(LevelSelectScreen levelSelect) {
        this.levelSelectScreen = levelSelect;
    }

    public void setDungeonScreen(DungeonScreen dungeon) {
        this.currentDungeon = dungeon;
    }

    /**
     * Next "dungeon" is really the screen that will be navigated 
     * to if this dungeon were to be completed
     * @param dungeon
     */
    public void setNextDungeon(Screen dungeon) {
        this.nextDungeon = dungeon;
    }
}

