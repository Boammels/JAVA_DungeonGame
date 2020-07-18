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

    public int getfatherX() {
        return fatherX;
    }

    public int getfatherY() {
        return fatherY;
    }

    public int getx() {
        return x;
    }

    public int gety() {
        return y;
    }


}