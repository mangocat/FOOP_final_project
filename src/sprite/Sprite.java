package sprite;

import utils.Direction;
import model.Team;

import java.awt.*;

public abstract class Sprite {
    protected Point location;
    protected Rectangle range;
    protected Direction face;
    protected Team team;
    protected int hp;
    protected int attackDistance;
    protected int damage;
    protected int front;
    protected int originAttackCd;
    protected int width;
    protected int height;

    public abstract void update();

    public abstract void render(Graphics g);

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public void takeDamage(int dmg) {
        hp -= dmg;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Team getTeam() {
        return team;
    }

    public void setFace(Direction face) {
        this.face = face;
    }

    public Direction getFace() {
        return face;
    }

    public void setFront(int x) {
        this.front = x;
    }

    public int getFront() {
        return (face == Direction.LEFT) ? range.x : range.x + range.width;
    }

    public void setRange(Rectangle range) {
        this.range = range;
    }

    public Rectangle getRange() {
        return range;
    }

    public int getEnemyBattleLine() {
        return team.getEnemyBattleLine();
    }

    public int getAttackDistance() {
        return attackDistance;
    }

    public int getDamage() {
        return damage;
    }

    public int getOriginAttackCd() {
        return originAttackCd;
    }

    public boolean isAlive() {
        return hp > 0;
    }
}
