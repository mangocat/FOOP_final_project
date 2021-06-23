import java.awt.*;

public class Ninja extends Unit {
    static final int attackCd = 30; // attack every 30 updates
    static final int attackDistance = 50;
    static final int maxHp = 100;
    static final int speed = 3;
    static final int ninjaDamage = 20;

    public Ninja() {
        super(speed, attackDistance, maxHp, ninjaDamage, attackCd);
        stateMap.put("attack", new AttackState(this, "ninja"));
        stateMap.put("idle", new IdleState(this, "ninja"));
        stateMap.put("move", new MoveState(this, "ninja"));
        stateMap.put("dead", new DeadState(this, "ninja"));
        currentState = stateMap.get("move");
    }
}
