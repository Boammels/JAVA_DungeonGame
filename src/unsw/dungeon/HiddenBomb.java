package unsw.dungeon;

public class HiddenBomb extends Entity implements Collectable{

    private Dungeon dungeon;
    public boolean pickedUp;

    public HiddenBomb(Dungeon dungeon, int x, int y) {
        super(x,y);
        this.dungeon = dungeon;
    }

    public void activate() {
        pickUp();
    }

    @Override
    public int handlePlayer(Player player) {
        pickUp();
        if(player.haveShield()) {
            player.setShield(0);
            dungeon.removeFromInventory(player.getShieldObject());
        }
        else {
            dungeon.killPlayer();
        }
        return 1;
    }

    @Override
    public void setPickedUp(boolean value) {
        // TODO Auto-generated method stub
        this.pickedUp = value;
    }

    @Override
    public void pickUp() {
        dungeon.moveToInventory(this);
        dungeon.removeFromInventory(this);
        setShow(false);
    }

    public void hide() {
        setShow(false);  
    }

    
    
}