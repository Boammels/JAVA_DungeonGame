package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

public class GoalGroup implements GoalComponent {
    private List<GoalComponent> goalComponents;
    private boolean complete = false;
    private String operator;

    /**
     * Create a GoalGroup that will either require all goals to be completed under it (AND)
     * or just 1 (OR)
     * @param operator
     */
    public GoalGroup(String operator) {
        goalComponents = new ArrayList<GoalComponent>();
        this.operator = operator;
    }

    /**
     * Gets what operator this GoalGroup is
     * @return will be "AND" or "OR"
     */
    public String getOperator() {
        return operator;
    }
    
    /**
     * Return a list of all the goals underneath this group
     */
    public String getGoals() {
        String allGoals = "(";
        for (GoalComponent g : goalComponents) {
            if (goalComponents.indexOf(g) == (goalComponents.size() -1)) {
                allGoals += g.getGoals() + ")";
            } else {
                allGoals += g.getGoals() + " " + operator + " ";
            }
        }
        System.out.println(allGoals);
        return allGoals;
    }

    /**
     * Reset all goals to incomplete
     */
    public void clear() {
        for (GoalComponent g : goalComponents){
            complete = false;
            g.clear();
        }
    }

    /**
     * Create a new Goal from the string given and it to this GoalGroup
     * @param goal
     */
    public void addGoal(String goal) {
        Goal goalLeaf = new Goal(goal);
        goalComponents.add(goalLeaf);
    }

    /**
     * Add a goal to this GoalGroups list of GoalComponents, given a GoalComponent to add
     * @param goal
     */
    public void addGoal(GoalComponent goal) {
        goalComponents.add(goal);
    }

    /**
     * Set a goal to complete, recursively go down the tree of goals and when we find the goal
     * leaf and set it to complete, we recurse back up the tree and correct all of the GoalGroups
     * to account for this new goal being completed
     * @param goalPassed - the name of the goal that has been passed
     * @return indicates whether the GoalComponent recursed down has completed its goal
     */
    public boolean complete(String goalPassed) {
        for (GoalComponent g : goalComponents){
            boolean result = g.complete(goalPassed);
            if (result) {
                if (getOperator().equals("AND")) {
                    for (GoalComponent gg : goalComponents){
                        if (!gg.getGoalComplete()) {
                            // Exit must be last to complete in an AND GoalGroup
                            if (goalPassed.equals("exit") && g.getGoals().equals("exit")) {
                                // Set the exit Goal back to false
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
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * Sets this groups goalComplete flag
     */
    public void setGoalComplete(boolean complete) {
        this.complete = complete;
    }

    /**
     * Gets whether or not this groups goal is complete
     */
    public boolean getGoalComplete() {
        return complete;
    }
}