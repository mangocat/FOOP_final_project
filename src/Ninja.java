import java.awt.*;

public class Ninja extends Unit {
    static final int attackCd = 5;
    static final int attackDistance = 50;
    static final int maxHp = 100;
    static final int speed = 3;

    public Ninja() {
        super(speed, attackDistance, maxHp);
        stateMap.put("attack", new AttackState(this, "ninja"));
        stateMap.put("idle", new IdleState(this, "ninja"));
        stateMap.put("move", new MoveState(this, "ninja"));
        stateMap.put("dead", new DeadState(this, "ninja"));
    }
}
