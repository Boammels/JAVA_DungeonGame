package unsw.dungeon;

public class Goal implements GoalComponent {
    private String goalType;
    
    public Goal(String goalType) {
        this.goalType = goalType;
    }

    public String getGoals() {
        return goalType;
    }

    public int remove(String goalPassed) {
        if (goalType.equals(goalPassed)) {
            return 1;
        } else {
            return 0;
        }
    }
}