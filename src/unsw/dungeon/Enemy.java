package unsw.dungeon;

public class Enemy extends Entity {

    public Enemy (int x, int y) {
        super(x,y);
    }

    public void move () {
        int x = super.getX();
        int y = super.getY();
        int left = dfs(x-1, y);
        int right = dfs(x+1, y);
        int up = dfs(x,y-1);
        int down = dfs(x, y+1);
        int direction = getDirection(left,right,up,down);
        if (direction == 1){
            super.setX(super.getX() - 1);

        }

        }

    }

    public int getDirection (int left, int right, int up, int down) {
        if(left <= right && left <= up && left <= down && left != 100000) {
            return 1;  //(1 means left)
        } else if(right <= up && right <= down && right != 100000) {
            return 2; //2 means right;
        } else if(up <= down && up != 100000) {
            return 3;   //3 means up
        } else {
            return 4;   //4 means down
        }
    }

    public int dfs(int x, int y) {
        return 100000;
    }
    
}