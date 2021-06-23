import java.awt.*;

public class Ninja extends Unit {
    static final int attackCd = 30; // attack every 30 updates
    static final int attackDistance = 50;
    static final int maxHp = 100;
    static final int speed = 3;
    static final int ninjaDamage = 20;

    public Ninja() {
        super(speed, attackDistance, maxHp, ninjaDamage, attackCd);
        State attack = new AttackState(this, "ninja", 50);
        int width = attack.getImageWidth();
        int height = attack.getImageHeight();
        // set range
        stateMap.put("attack", attack);
        stateMap.put("idle", new IdleState(this, "ninja", 50));
        stateMap.put("move", new MoveState(this, "ninja", 50));
        stateMap.put("dead", new DeadState(this, "ninja", 50));
        currentState = stateMap.get("move");
    }
}
