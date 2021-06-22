import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Unit extends Sprite {
    protected Map<String, State> stateMap = new HashMap<>();
    protected State currentState;
    protected int movementSpeed;

    public Unit(int speed, int attackDist, int hp){
        currentState = new Moving();
        movementSpeed = speed;
        attackDistance = attackDist;
        this.hp = hp;
    }

    @Override
    public void update(int enemyBattleLine) {
        currentState.update();
        currentState.doAction();
    }

    @Override
    public void render(Graphics g) {
        currentState.render();
    }

    public void setState(State state){ currentState = state; }
    public State getState(String stateName){
        return stateMap.get(stateName);
    }

    public int getMovementSpeed() {
        return movementSpeed;
    }
}
