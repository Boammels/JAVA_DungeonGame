package unsw.dungeon;

/**
 * Composte design pattern used to handle OR and AND goal types, as we need to store groups of goals
 * Groups can be either of type AND or OR, meaning either everything in the group must be complete for
 * the group to be complete, or only one thing in the group needs to be complete for the group to be
 * considered complete
 */
public interface GoalComponent {
    public String getGoals();
    public boolean complete(String goal);
    // public int getSize();
    public void setGoalComplete(boolean complete);
    public boolean getGoalComplete();
}