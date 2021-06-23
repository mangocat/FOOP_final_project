import java.awt.*;

public abstract class Sprite {
    protected Point location = new Point();
    protected Rectangle range;
    protected Direction face;
    protected Team team;
    protected int hp;
    protected int attackDistance;
    protected int damage;
    protected int front;
    protected int originAttackCd;

    public abstract void update();

    public abstract void render(Graphics g);

    public void takeDamage(int dmg){ hp -= dmg; }

    public void setTeam(Team team){
        this.team = team;
    }

    public Team getTeam() {
        return team;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public Point getLocation(){
        return location;
    }

    public void setFace(Direction face) {
        this.face = face;
    }

    public Direction getFace() {
        return face;
    }

    public void setFront(int x){ this.front = x; }

    public int getFront(){
        return (face==Direction.LEFT)? location.x : location.x + range.width;
    }
    public void setRange(Rectangle range){ this.range = range; }
    public Rectangle getRange(){ return range; }

    public int getEnemyBattleLine(){ return team.getEnemyBattleLine(); }

    public boolean isAlive() { // make sure that removeSprite sets the team to null
        return hp > 0;
    }
}
