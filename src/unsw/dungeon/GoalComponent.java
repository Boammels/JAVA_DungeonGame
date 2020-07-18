package unsw.dungeon;

/**
 * Composte design pattern used to handle OR and AND goal types
 * Everything in a mark group is an AND, markcomponents on the same tree level are ORs
 */
public interface GoalComponent {
    public String getGoals();
    public int remove(String goal);
}