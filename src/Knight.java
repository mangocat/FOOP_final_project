import java.util.Collection;

public class Knight extends Unit {
    // static final int KNIGHT_HEIGHT = 200;
    // static final int KNIGHT_WIDTH = 185;
    static final int attackCd = 100; // attack every 30 updates
    static final int attackDistance = 50;
    static final int maxHp = 100;
    static final int speed = 1;
    static final int knightDamage = 20;

    public Knight(double scale) {
        super(speed, attackDistance, maxHp, knightDamage, attackCd);

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

        stateMap.put("attack", new AttackState(this, "knight", scale, stateHandler));
        stateMap.put("idle", new IdleState(this, "knight", scale, stateHandler));
        stateMap.put("move", new MoveState(this, "knight", scale, stateHandler));
        stateMap.put("dead", new DeadState(this, "knight", scale, stateHandler));
        currentState = stateMap.get("move");

    }
}
