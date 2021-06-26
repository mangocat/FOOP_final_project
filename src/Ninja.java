import java.util.Collection;

public class Ninja extends Unit {
    static final int attackCd = 100; 
    static final int attackDistance = 30;
    static final int maxHp = 100;
    static final int speed = 1;
    static final int ninjaDamage = 20;

    public Ninja(double scale) {
        super(speed, attackDistance, maxHp, ninjaDamage, attackCd);

        StateHandler nullHandler = new StateHandler(null) {
            @Override
            public boolean isThis(State currentState) {
                throw new RuntimeException("undefined state");
            }
        };

        StateHandler idleHandler = new StateHandler(nullHandler) {
            @Override
            public State nextState(State currentState) {
                if (isThis(currentState)) return stateMap.get("idle");
                return super.nextState(currentState);
            }

            @Override
            public boolean isThis(State currentState) {
                return currentAttackCd > 0;
            }
        };

        StateHandler attackStateHandler = new StateHandler(idleHandler) {
            @Override
            public State nextState(State currentState) {
                if (isThis(currentState)) return stateMap.get("attack");

                return super.nextState(currentState);
            }

            @Override
            public boolean isThis(State currentState) {
                Collection<Sprite> attackableUnits = team.getWorld().getSprites(currentState.unit, face, attackDistance);
                return currentAttackCd <= 0 && !attackableUnits.isEmpty();
            }
        };

        StateHandler moveStateHandler = new StateHandler(attackStateHandler) {
            @Override
            public State nextState(State currentState) {
                if (isThis(currentState)) return stateMap.get("move");
                return super.nextState(currentState);
            }

            @Override
            public boolean isThis(State currentState) {
                boolean isMoving = (face == Direction.LEFT && (getEnemyBattleLine() + attackDistance) < front) || (face == Direction.RIGHT && (front + attackDistance) < getEnemyBattleLine());
                return isMoving;
            }
        };

        StateHandler deadHandler = new StateHandler(moveStateHandler) {
            @Override
            public State nextState(State currentState) {
                if (isThis(currentState)) return stateMap.get("dead");
                return super.nextState(currentState);
            }

            @Override
            public boolean isThis(State currentState) {
                return !isAlive();
            }
        };

        this.stateHandler = deadHandler;

        stateMap.put("attack", new AttackState(this, "ninja", scale, stateHandler));
        stateMap.put("idle", new IdleState(this, "ninja", scale, stateHandler));
        stateMap.put("move", new MoveState(this, "ninja", scale, stateHandler));
        stateMap.put("dead", new DeadState(this, "ninja", scale, stateHandler));
        currentState = stateMap.get("move");

    }
}
