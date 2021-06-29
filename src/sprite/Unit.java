package sprite;

import state.State;
import utils.StateHandler;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Unit extends Sprite {
    protected Map<String, State> stateMap = new HashMap<>();
    protected State currentState;
    protected int movementSpeed;
    protected int currentAttackCd;
    protected StateHandler stateHandler;
    protected int height;
    protected int width;

    public Unit(int speed, int attackDist, int hp, int damage, int attackCd) {
        movementSpeed = speed;
        attackDistance = attackDist;
        this.hp = hp;
        this.damage = damage;
        this.originAttackCd = attackCd;
        this.currentAttackCd = 0;
    }

    @Override
    public void update() {
        currentAttackCd -= 1;
        currentState.update();
        currentState.doAction();
    }

    @Override
    public void render(Graphics g) {
        currentState.render(g);
    }

    public void setState(State state) {
        state.reset();
        currentState = state;
    }

    public State getState(String stateName) {
        return stateMap.get(stateName);
    }

    public int getMovementSpeed() {
        return movementSpeed;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void setCurrentAttackCd(int cd) {
        currentAttackCd = cd;
    }

    public int getCurrentAttackCd() {
        return currentAttackCd;
    }
}
