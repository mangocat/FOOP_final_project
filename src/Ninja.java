import java.awt.*;

public class Ninja extends Unit {
    static final int attackCd = 100; // attack every 30 updates
    static final int attackDistance = 50;
    static final int maxHp = 100;
    static final int speed = 3;
    static final int ninjaDamage = 20;

    public Ninja() {
        super(speed, attackDistance, maxHp, ninjaDamage, attackCd);
        this.width = 100;
        State attack = new AttackState(this, "ninja", width);
        height = attack.getImageHeight();
        // set range, need location to set range.x, range.y (the first 2 parameters of
        // Rectangle)
        // setRange(new Rectangle(0, 0, width, height));
        stateMap.put("attack", attack);
        stateMap.put("idle", new IdleState(this, "ninja", width));
        stateMap.put("move", new MoveState(this, "ninja", width));
        stateMap.put("dead", new DeadState(this, "ninja", width));
        currentState = stateMap.get("move");
    }
}
