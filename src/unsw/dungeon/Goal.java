package unsw.dungeon;

public class Goal implements GoalComponent {
    private String goalType;
    private boolean complete = false;
    
    /**
     * Creates a singular Goal
     * @param goalType
     */
    public Goal(String goalType) {
        this.goalType = goalType;
    }

    /**
     * Returns the name of this goal
     */
    public String getGoals() {
        return goalType;
    }

    /**
     * Sets the goalComplete flag
     */
    public void setGoalComplete(boolean complete) {
        this.complete = complete;
    }

    /**
     * Gets whether this Goal is complete
     */
    public boolean getGoalComplete() {
        return complete;
    }

    /**
     * Given a goal that has been passed, set it to clear if it corresponds to this Goal
     */
    public boolean complete(String goalPassed) {
        if (goalType.equals(goalPassed)) {
            setGoalComplete(true);
            return true;
        } else {
            return false;
        }
    }

    // public int getSize() {
    //     if (getGoalComplete()) {
    //         return 0;
    //     } else {
    //         return 1;
    //     }
    // }

    // public boolean isLayerComplete() {
    //     return getGoalComplete();
    // }
}