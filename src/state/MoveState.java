package state;

import utils.StateHandler;
import utils.ImageReader;
import utils.Direction;
import sprite.Unit;

import java.awt.*;

public class MoveState extends State {
    public MoveState(Unit u, String unitType, double scale, StateHandler stateHandler) {
        super(u, "move", ImageReader.read("assets/" + unitType + "/move", scale), stateHandler);
    }

    @Override
    public void doAction() {
        Direction face = unit.getFace();
        Rectangle range = unit.getRange();
        int enemyBattleLine = unit.getEnemyBattleLine();
        int attackDistance = unit.getAttackDistance();
        int front = unit.getFront();
        int moveDistance = Math.min(unit.getMovementSpeed(), Math.max(0, Math.abs(front - enemyBattleLine) - attackDistance));

        if (face == Direction.LEFT) {
            range.x -= moveDistance;
            front -= moveDistance;
        } else {
            range.x += moveDistance;
            front += moveDistance;
        }

        unit.setRange(range);
        unit.setFront(front);
    }

    @Override
    public boolean remains(State nextState) {
        return nextState instanceof MoveState;
    }
}
