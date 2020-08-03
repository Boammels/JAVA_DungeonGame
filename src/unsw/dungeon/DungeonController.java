package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
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

    private List<ImageView> initialEntities;

    private Player player;

    private Dungeon dungeon;

    private Screen nextDungeon;
    private GameOverScreen gameOverScreen;
    private DungeonScreen currentDungeon;

    //private MediaPlayer musicPlayer;
    //private AudioClip audio;

    public DungeonController(Dungeon dungeon, List<ImageView> initialEntities, Screen nextDungeon) {
        this.dungeon = dungeon;
        this.player = dungeon.getPlayer();
        this.initialEntities = new ArrayList<>(initialEntities);
        this.nextDungeon = nextDungeon;
        dungeon.setController(this);
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
        
        gameObjectives = new Text();
        gameObjectives.setFont(new Font("Arial", 15));
        gameObjectives.textProperty().bind(dungeon.getGoalText());
        squares.add(gameObjectives, 0, dungeon.getHeight());
        squares.setColumnSpan(gameObjectives, dungeon.getWidth());
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

