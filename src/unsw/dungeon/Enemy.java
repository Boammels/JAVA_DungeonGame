package unsw.dungeon;

import javafx.util.Duration;
import java.util.LinkedList;
import java.util.Queue;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;


public class Enemy extends Entity {
    
    private Dungeon dungeon;
    private Player target;
    private int lastX;
    private int lastY;
    private final Timeline timeline = new Timeline();

    public Enemy (Dungeon dungeon, Player player, int x, int y, int moveSpeed) {
        super(x,y);
        this.dungeon = dungeon;
        
        this.target = player;
        lastX = 0;
        lastY = 0;
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(moveSpeed), e -> move()));
        timeline.setCycleCount(Animation.INDEFINITE);
    }
    
    public void go() {
        timeline.play();
    }

    /**
     * Let the enemy move
     */
    public void move () {
        if (target.havePotion()) {
            leave();
        } else {
            towards();
        }
        for(Entity e : dungeon.getEntities()) {
            if(getX() == e.getX() && getY() == e.getY()) {
                if(!e.equals(this)) {
                    e.teleportEnemy(this);
                    break;
                }
            }
        }
        if(getX() == target.getX() && getY() == target.getY()) {
            handlePlayer(target);
        }
        checkSquare();
    }

    public void checkSquare() {
        if (!getShow().get()) {
            return;
        }
        for(Entity e : dungeon.getEntities()) {
            if(e instanceof HiddenBomb) {
                if(getX()!=e.getX() || getY()!=e.getY()){
                    return;
                }
                HiddenBomb bomb = (HiddenBomb)e;
                bomb.activate();
                dungeon.decreaseEnemyCount();
                setShow(false);
            }
        }
    }

    /**
     * A BFS function to search for the best way towards the player.
     * regardless of the portal
     */
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

    /**
     * Getaway from the player when the player has a potion.
     */
    public void leave() {
        int x = getX();
        int y = getY();
        int playerX = target.getX();
        int playerY = target.getY();
        lastX = x;
        lastY = y;
        if(playerX > x) {
            if(playerY >= y) {
                moveLeftUp(x, y, playerX, playerY);
            } else {
                moveLeftDown(x, y, playerX, playerY);
            }   
        } else {
            if (playerY >= y) {
                moveRightUp(x, y, playerX, playerY);
            } else {
                moveRightDown(x, y, playerX, playerY);
            }
        }
    }

    /**
     * Four functions handling moving away from the player
     * @param x current location
     * @param y current location
     * @param playerX player's location
     * @param playerY player's location
     */
    public void moveLeftUp(int x, int y, int playerX, int playerY) {
        if(!checkWall(x-1,y)) { 
            setX(x-1);
            setY(y);
        } else if (!checkWall(x,y-1)) {
            setX(x);
            setY(y-1);
        } else if (playerX - x > playerY - y ) {
            
            if(!checkWall(x,y+1)) {
                setX(x);
                setY(y+1);
            } else {
                setX(x+1);
                setY(y);
            }
        } else {
            if(!checkWall(x+1,y)) {
                setX(x+1);
                setY(y);
            } else {
                setX(x);
                setY(y+1);
            }
        }
    }
    public void moveLeftDown(int x, int y, int playerX, int playerY) {
        if(!checkWall(x-1,y)) { 
            setX(x-1);
            setY(y);
        } else if (!checkWall(x,y+1)) {
            setX(x);
            setY(y+1);
        } else if (playerX - x > y - playerY ) {
            if(!checkWall(x,y-1)) {
                setX(x);
                setY(y-1);
            } else {
                setX(x+1);
                setY(y);
            }
        } else {
            if(!checkWall(x+1,y)) {
                setX(x+1);
                setY(y);
            } else {
                setX(x);
                setY(y-1);
            }
        }
    }
    public void moveRightUp(int x, int y, int playerX, int playerY) {
        if(!checkWall(x+1,y)) { 
            setX(x+1);
            setY(y);
        } else if (!checkWall(x,y-1)) {
            setX(x);
            setY(y-1);
        } else if ( x - playerX > playerY - y ) {
            
            if(!checkWall(x,y+1)) {
                setX(x);
                setY(y+1);
            } else {
                setX(x-1);
                setY(y);
            }
        } else {
            if(!checkWall(x-1,y)) {
                setX(x-1);
                setY(y);
            } else {
                setX(x);
                setY(y+1);
            }
        }
    }
    public void moveRightDown(int x, int y, int playerX, int playerY) {
        if(!checkWall(x+1,y)) { 
            setX(x+1);
            setY(y);
        } else if (!checkWall(x,y+1)) {
            setX(x);
            setY(y+1);
        } else if (x - playerX > y - playerY ) {
            
            if(!checkWall(x,y-1)) {
                setX(x);
                setY(y-1);
            } else {
                setX(x-1);
                setY(y);
            }
        } else {
            if(!checkWall(x-1,y)) {
                setX(x-1);
                setY(y);
            } else {
                setX(x);
                setY(y-1);
            }
        }
    }

    /**
     * check if it is a wall at coordinate x,y
     * @param x
     * @param y
     * @return true if it is a wall
     */
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
        if (getShow().get()) {
            // If the player is touching an enemy, this method is called
            if(target.haveWeapon() || target.havePotion() || target.haveShield()) {
                dungeon.decreaseEnemyCount();
                dungeon.checkEnemyGoal();
                setShow(false);
            }
            target.beAttacked();
        }
        return 1;
    }
    
}