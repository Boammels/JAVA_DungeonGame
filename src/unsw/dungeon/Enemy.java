package unsw.dungeon;


public class Enemy extends Entity {

    public static final int LEFT = 1;
    public static final int RIGHT = 2;
    public static final int UP = 3;
    public static final int DOWN = 4;
    public static final int FAILMOVE = Integer.MAX_VALUE;
    
    private Dungeon dungeon;
    private int visited[][];
    private Player target;
    private boolean alive;

    public Enemy (Dungeon dungeon, Player player, int x, int y) {
        super(x,y);
        this.dungeon = dungeon;
        for (int i = 0; i < dungeon.getWidth(); i++) {
            for (int j = 0; j < dungeon.getHeight(); j++) {
                visited[i][j] = 0;
            } 
        }
        this.target = player;
        alive = true;
    }

    public void move () {
        if (!alive){
            return; 
        }
        if (target.havePotion()) {
            if(meetPlayer(getX(), getY())) {
                handlePlayer(target);
            }
            return;
        } else {
            towards();
        }
        if(meetPlayer(getX(), getY())) {
            handlePlayer(target);
        }
    }

    public void towards() {
        int x = getX();
        int y = getY();
        int left = dfs(x-1, y);
        int right = dfs(x+1, y);
        int up = dfs(x,y-1);
        int down = dfs(x, y+1);
        int direction = getDirection(left,right,up,down);
        if (direction == LEFT){
            x().set(x-1);
        } else if (direction == RIGHT) {
            x().set(x+1);
        } else if (direction == UP) {
            y().set(y-1);
        } else if (direction == DOWN) {
            y().set(y+1);
        }
    } 

    public int getDirection (int left, int right, int up, int down) {
        if(left <= right && left <= up && left <= down && left != FAILMOVE) {
            return LEFT;  //(1 means left)
        } else if(right <= up && right <= down && right != FAILMOVE) {
            return RIGHT; //2 means right;
        } else if(up <= down && up != FAILMOVE) {
            return UP;   //3 means up
        } else {
            return DOWN;   //4 means down
        }
    }

    public int dfs(int x, int y) {
        if(x >= dungeon.getWidth() || x < 0) {
            return FAILMOVE;
        } else if (y >= dungeon.getHeight() || y < 0) {
            return FAILMOVE;
        } else if (isWall(x,y)) {
            return FAILMOVE;
        } else if (visited[x][y] == 1) {
            return FAILMOVE;
        } else if (meetPlayer(x,y)) {
            return 1;
        }

        visited[x][y] = 1;
        int left = dfs(x-1,y);
        int right = dfs(x+1,y);
        int up = dfs(x,y-1);
        int down = dfs(x,y+1);
        
        visited[x][y] = 0;
        int ans = min(left,right,up,down);
        if (ans == FAILMOVE) {
            return FAILMOVE;
        } else {
            return ans+1;
        }
    }
    

    public int min(int a, int b, int c, int d) {
        if(a<=b && a<=c && a<=d) {
            return a;
        } else if (b<=c && b<=d) {
            return b;
        } else if (c<=d) {
            return c;
        } else return d;
    }

    public boolean meetPlayer(int x, int y) {
        return x == target.getX() && y == target.getY();
    }

    public boolean isWall(int x, int y) {
        for (Entity e : dungeon.getEntities()) {
            if (e instanceof Wall) {
                if(x == e.getX() && y == e.getY()) {
                    return true;
                }
            }
        }
        return false;
    }

    public void handlePlayer(Player p) {
        // If the player is touching an enemy, this method is called
        if(target.haveWeapon()) {
            alive = false;
        }
        target.beAttacked();
    }
    
}