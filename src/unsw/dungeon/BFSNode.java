package unsw.dungeon;

public class BFSNode {
    private int fatherX;
    private int fatherY;
    private int x;
    private int y;

    public BFSNode(int x, int y, int fatherX, int fatherY) {
        this.x = x;
        this.y = y;
        this.fatherX = fatherX;
        this.fatherY = fatherY;
    }

    /**
     * @return the previous x value
     */
    public int getfatherX() {
        return fatherX;
    }
    /**
     * @return the previous y value
     */
    public int getfatherY() {
        return fatherY;
    }

    /**
     * @return current x value
     */
    public int getx() {
        return x;
    }
    /**
     * @return current y value
     */
    public int gety() {
        return y;
    }


}