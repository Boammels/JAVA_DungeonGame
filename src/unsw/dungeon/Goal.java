package unsw.dungeon;

public class Goal implements GoalComponent {
    private String goalType;
    private boolean complete = false;
    
    public Goal(String goalType) {
        this.goalType = goalType;
    }

    public String getGoals() {
        return goalType;
    }

    public void setGoalComplete(boolean complete) {
        this.complete = complete;
    }

    public boolean getGoalComplete() {
        return complete;
    }

    public boolean complete(String goalPassed) {
        if (goalType.equals(goalPassed)) {
            setGoalComplete(true);
            return true;
        } else {
            return false;
        }
    }

    public int getSize() {
        if (getGoalComplete()) {
            return 0;
        } else {
            return 1;
        }
    }

    public boolean isLayerComplete() {
        return getGoalComplete();
    }
}