package unsw.dungeon;

public class ControllerStateObserver implements StateObserver {
    DungeonController controller;

    public ControllerStateObserver(DungeonController controller) {
        this.controller = controller;
    }

    public void updateState(Dungeon d) {
        controller.handleStateChange(d.getState());
    }
}