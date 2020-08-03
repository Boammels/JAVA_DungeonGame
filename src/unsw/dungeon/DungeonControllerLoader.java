package unsw.dungeon;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.File;

/**
 * A DungeonLoader that also creates the necessary ImageViews for the UI,
 * connects them via listeners to the model, and creates a controller.
 * @author Robert Clifton-Everest
 *
 */
public class DungeonControllerLoader extends DungeonLoader {

    private List<ImageView> entities;

    //Images
    private Image playerImage;
    private Image playerImage2;
    private Image wallImage;
    private Image exitImage;
    private Image boulderImage;
    private Image switchImage;
    private Image potionImage;
    private Image treasureImage;
    private Image portalImage;
    private Image weaponImage;
    private Image enemyImage;
    private Image enemyImage2;
    private Image doorImage;
    private Image keyImage;
    private Image bombImage;
    private Image openDoor;
    private Image wolfImage;
    private Image wolfImage2;
    private Image woodenSword;
    private Image shieldImage;

    public DungeonControllerLoader(String filename)
            throws FileNotFoundException {
        super(filename);
        entities = new ArrayList<>();
        playerImage = new Image((new File("images/human_new.png")).toURI().toString());
        playerImage2 = new Image((new File("images/human_new2.png")).toURI().toString());
        wallImage = new Image((new File("images/wall.png")).toURI().toString());
        exitImage = new Image((new File("images/exit.png")).toURI().toString());
        boulderImage = new Image((new File("images/boulder.png")).toURI().toString());
        switchImage = new Image((new File("images/pressure_plate.png")).toURI().toString());
        potionImage = new Image((new File("images/potion.png")).toURI().toString());
        treasureImage = new Image((new File("images/treasure.png")).toURI().toString());
        portalImage = new Image((new File("images/portal.png")).toURI().toString());
        weaponImage = new Image((new File("images/greatsword_1_new.png")).toURI().toString());
        enemyImage = new Image((new File("images/deep_elf_master_archer.png")).toURI().toString());
        enemyImage2 = new Image((new File("images/deep_elf_master_archer2.png")).toURI().toString());
        doorImage = new Image((new File("images/closed_door.png")).toURI().toString());
        keyImage = new Image((new File("images/key.png")).toURI().toString());
        bombImage = new Image((new File("images/bomb.png")).toURI().toString());
        openDoor = new Image((new File("images/open_door.png")).toURI().toString());
        wolfImage = new Image((new File("images/hound.png")).toURI().toString());
        wolfImage2 = new Image((new File("images/hound2.png")).toURI().toString());
        woodenSword = new Image((new File("images/wooden_sword.png")).toURI().toString());
        shieldImage = new Image((new File("images/shield.png")).toURI().toString());
    }

    @Override
    public void onLoad(Entity player) {
        ImageView view = new ImageView(playerImage);
        ImageView view2 = new ImageView(playerImage2);
        addEntity(player, view);
        addEntity(player, view2);
        view2.visibleProperty().bind(player.getShow().not());
    }

    @Override
    public void onLoad(Wall wall) {
        ImageView view = new ImageView(wallImage);
        addEntity(wall, view);
    }

    @Override
    public void onLoad(Exit exit) {
        ImageView view = new ImageView(exitImage);
        addEntity(exit, view);
    }

    @Override
    public void onLoad(Boulder boulder) {
        ImageView view = new ImageView(boulderImage);
        addEntity(boulder, view);
    }

    @Override
    public void onLoad(Switch switchObject) {
        ImageView view = new ImageView(switchImage);
        addEntity(switchObject, view);
    }
    
    public void onLoad(Potion potion) {
        ImageView view = new ImageView(potionImage);
        addEntity(potion, view);
    }

    @Override
    public void onLoad(Treasure treasure) {
        ImageView view = new ImageView(treasureImage);
        addEntity(treasure, view);
    }

    @Override
    public void onLoad(Portal portal) {
        ImageView view = new ImageView(portalImage);
        addEntity(portal, view);
    }

    @Override
    public void onLoad(Weapon weapon, int type) {
        ImageView view;
        if (type == 1) {
            view = new ImageView(weaponImage);
        } else {
            view = new ImageView(woodenSword);
        }
        addEntity(weapon, view);
    }

    @Override
    public void onLoad(Enemy enemy, int type) {
        ImageView view;
        ImageView view2;
        if (type == 0) {
            view = new ImageView(enemyImage);
            view2 = new ImageView(enemyImage2);
        } else {
            view = new ImageView(wolfImage);
            view2 = new ImageView(wolfImage2);
        }
        addEntity(enemy, view);
        addEntity(enemy, view2);
        view.visibleProperty().bind(Bindings.when(enemy.getShow()).then(enemy.showAnimation()).otherwise(enemy.getShow()));
        view2.visibleProperty().bind(Bindings.when(enemy.getShow()).then(enemy.showAnimation().not()).otherwise(enemy.getShow()));
    }

    @Override
    public void onLoad(Door door) {
        ImageView closedView = new ImageView(doorImage);
        ImageView openView = new ImageView(openDoor);
        addEntity(door, closedView);
        addEntity(door, openView);
        openView.visibleProperty().bind(door.getShow().not());
    }

    @Override
    public void onLoad(Key key) {
        ImageView view = new ImageView(keyImage);
        addEntity(key, view);
    }

    @Override
    public void onLoad(Shield shield) {
        ImageView view = new ImageView(shieldImage);
        addEntity(shield, view);
    }

    @Override
    public void onLoad(Shelter shelter) {
        ImageView view = new ImageView(wallImage);
        addEntity(shelter, view);
    }

    @Override
    public void onLoad(HiddenBomb bomb) {
        ImageView view = new ImageView(bombImage);
        addEntity(bomb, view);
    }

    private void addEntity(Entity entity, ImageView view) {
        trackPosition(entity, view);
        entities.add(view);
    }

    /**
     * Set a node in a GridPane to have its position track the position of an
     * entity in the dungeon.
     *
     * By connecting the model with the view in this way, the model requires no
     * knowledge of the view and changes to the position of entities in the
     * model will automatically be reflected in the view.
     * @param entity
     * @param node
     */
    private void trackPosition(Entity entity, Node node) {
        GridPane.setColumnIndex(node, entity.getX());
        GridPane.setRowIndex(node, entity.getY());
        entity.x().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                GridPane.setColumnIndex(node, newValue.intValue());
            }
        });
        entity.y().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                GridPane.setRowIndex(node, newValue.intValue());
            }
        });
        node.visibleProperty().bind(entity.getShow());
    }

    /**
     * Create a controller that can be attached to the DungeonView with all the
     * loaded entities.
     * @return
     * @throws FileNotFoundException
     */
    public DungeonController loadController(DungeonScreen nextDungeon) throws FileNotFoundException {
        return new DungeonController(load(), entities, nextDungeon);
    }


}
