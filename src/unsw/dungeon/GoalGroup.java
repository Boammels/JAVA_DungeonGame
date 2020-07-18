package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

public class GoalGroup implements GoalComponent {
    private List<GoalComponent> goalComponents;
    private boolean complete = false;
    private String operator;

    public GoalGroup(String operator) {
        goalComponents = new ArrayList<GoalComponent>();
        this.operator = operator;
    }

    public String getOperator() {
        return operator;
    }

    public String getGoals() {
        String allGoals = "";
        for (GoalComponent g : goalComponents){
            allGoals += g.getGoals();
            // System.out.println("\n");
        }
        return allGoals;
    }

    public void addGoal(String goal) {
        Goal goalLeaf = new Goal(goal);
        goalComponents.add(goalLeaf);
    }

    public void addGoal(GoalComponent goal) {
        goalComponents.add(goal);
    }

    // When we remove from a goal component we are checking to see if the game has been completed
    public boolean complete(String goalPassed) {
        for (GoalComponent g : goalComponents){
            boolean result = g.complete(goalPassed);
            if (result) {
                if (getOperator().equals("AND")) {
                    // int valid = 0;
                    for (GoalComponent gg : goalComponents){
                        if (!gg.getGoalComplete()) {
                            if (goalPassed.equals("exit") && g.getGoals().equals("exit")) {
                                g.setGoalComplete(false);
                            }
                            return false;
                        }
                    }
                    setGoalComplete(true);
                    return true;
                } else {
                    for (GoalComponent gg : goalComponents){
                        if (gg.getGoalComplete()) {
                            setGoalComplete(true);
                            return true;
                        }
                    }
                    // if (goalPassed.equals("exit") && g.getGoals().equals("exit")) {
                    //     g.setGoalComplete(false);
                    // }
                    return false;
                }
            }
        }
        return false;
    }

    // Exit must be the last in its component before we count it
    // If any goal component empties, then the component above is completed
    // if (g.getGoalComplete() && g.getGoals().equals("exit")) {
    //     if (getSize() != 0) {
    //         g.setGoalComplete(false);
    //     }
    // } 

    public int getSize() {
        int size = 0;
        for (GoalComponent g : goalComponents) {
            if (!g.getGoalComplete()) {
                size++;
            }
        }
        // System.out.println(size);
        return size;
    }

    public void setGoalComplete(boolean complete) {
        this.complete = complete;
    }

    public boolean getGoalComplete() {
        return complete;
    }

    public List<GoalComponent> getGoalComponents() {
        return goalComponents;
    }
}