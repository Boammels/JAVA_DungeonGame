package unsw.dungeon;

import java.util.LinkedList;
import java.util.Queue;

public class Enemy extends Entity {

    public static final int LEFT = 1;
    public static final int RIGHT = 2;
    public static final int UP = 3;
    public static final int DOWN = 4;
    public static final int FAILMOVE = Integer.MAX_VALUE;
    
    private Dungeon dungeon;
    private Player target;
    private int lastX;
    private int lastY;

    public Enemy (Dungeon dungeon, Player player, int x, int y) {
        super(x,y);
        this.dungeon = dungeon;
        
        this.target = player;
        lastX = 0;
        lastY = 0;
    }

    @Override
    public void move () {
        if (target.havePotion()) {
            return ;
        } else {
            towards();
        }
    }

    public void towards() {
        BFSNode [][] visited = new BFSNode[dungeon.getWidth()][dungeon.getHeight()];
        for (int i = 0; i < dungeon.getWidth(); i++) {
            for (int j = 0; j < dungeon.getHeight(); j++) {
                visited[i][j] = null;
            } 
        }
        int x = getX();
        int y = getY();
        Queue<BFSNode> queue = new LinkedList<BFSNode>();
        BFSNode now = new BFSNode(x,y,x,y);
        queue.add(now);
        BFSNode node = queue.poll();
        while(node != null) {
            x = node.getx();
            y = node.gety();
            if(checkWall(x,y)) {
                node = queue.poll();
            } else if (visited[x][y] != null) {
                node = queue.poll();
            } else if (x == lastX && y == lastY) {
                node = queue.poll();
            } else if (meetPlayer(x,y)){
                visited[x][y] = node;
                break;
            } else {
                BFSNode left = new BFSNode(x-1,y,x,y);
                BFSNode right = new BFSNode(x+1,y,x,y);
                BFSNode down = new BFSNode(x,y+1,x,y);
                BFSNode up = new BFSNode(x,y-1,x,y);
                queue.add(left);
                queue.add(right);
                queue.add(up);
                queue.add(down);
                visited[x][y] = node;
                node = queue.poll();
            }
        }
        x = target.getX();
        y = target.getY();
        if (visited[x][y] == null) {
            return;
        }
        int fatherX = visited[x][y].getfatherX();
        int fatherY = visited[x][y].getfatherY();
        while(fatherX != getX() || fatherY != getY()) {
            x = fatherX;
            y = fatherY;
            fatherX = visited[x][y].getfatherX();
            fatherY = visited[x][y].getfatherY();
        }
        lastX = getX();
        lastY = getY();
        setX(x);
        setY(y);
    }



    public boolean checkWall(int x,int y) {
        if(x < 0 || y < 0 || x >= dungeon.getWidth() || y >=dungeon.getHeight()) {
            return true;
        }
        for(Entity e : this.dungeon.getEntities()) {
            if(e.getX() == x && e.getY() == y) {
                if (e.isWall()) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean meetPlayer(int x, int y) {
        return x == target.getX() && y == target.getY();
    }

    @Override
    public int handlePlayer(Player p) {
        // If the player is touching an enemy, this method is called
        if(target.haveWeapon() || target.havePotion()) {
            dungeon.getEntities().remove(this);
            setX(0);
            setY(0);
        }
        target.beAttacked();
        return 1;
    }
    
}