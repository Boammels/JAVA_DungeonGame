package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

public class GoalGroup implements GoalComponent {
    private List<GoalComponent> goalComponents;

    public GoalGroup() {
        goalComponents = new ArrayList<GoalComponent>();
    }

    public String getGoals() {
        String allGoals = "";
        for (GoalComponent g : goalComponents){
            allGoals += g.getGoals();
        }
        return allGoals;
    }

    public void addGoal(String goal) {
        GoalComponent goalLeaf = new Goal(goal);
        goalComponents.add(goalLeaf);
    }

    public void addGoal(GoalComponent goal) {
        goalComponents.add(goal);
    }

    // When we remove from a goal component we are checking to see if the game has been completed
    public int remove(String goalPassed) {
        for (GoalComponent g : goalComponents){
            int result = g.remove(goalPassed);
            if (result == 2) {
                return 2;
            } else if (result == 1) {
                // Exit must be the last in its component before we count it
                if (!goalPassed.equals("exit")) {
                    goalComponents.remove(g);
                    if (goalComponents.isEmpty()) {
                        // If any goal component empties, that the dungeon has been completed
                        return 2;
                    }
                }
            }
        }
        return 0;
    }
}