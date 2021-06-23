import java.awt.*;

public class MoveState extends State{
    public MoveState(Unit u, String unitType){
        super(u, "move", ImageReader.read("assets/" + unitType + "/move"));
    }
    @Override
    public void doAction(){
        Direction face = unit.getFace();
        Rectangle range = unit.getRange();
        int enemyBattleLine = unit.getEnemyBattleLine();
        int attackDistance = unit.getAttackDistance();
        int front = unit.getFront();
        int moveDistance = Math.min(unit.getMovementSpeed(), Math.abs(front-enemyBattleLine)-attackDistance);
        // notify unit position change
        if(face == Direction.LEFT){
            range.x -= moveDistance;
            front -= moveDistance;
        }else{
            range.x += moveDistance;
            front += moveDistance;
        }
        unit.setRange(range);
        unit.setFront(front);
    }
    public void update(){
        // check if the unit is dead.
        if(!unit.isAlive()){
            State dead = unit.getState("dead");
            dead.update();
            unit.setState(dead);
            return;
        }
        Direction face = unit.getFace();
        Rectangle range = unit.getRange();
        int enemyBattleLine = unit.getEnemyBattleLine();
        int attackDistance = unit.getAttackDistance();
        int front = unit.getFront();
        State next;
        if((face == Direction.LEFT && enemyBattleLine+attackDistance >= front) || (face == Direction.RIGHT && front+attackDistance >= enemyBattleLine)){
            reset();
            // if the range is enough, try to stop and attack
            if(unit.getCurrentAttackCd() == 0){
                next = unit.getState("attack");
            }else{
                next = unit.getState("idle");
            }
            next.update();
            unit.setState(next);
            return;
        }
        currentPosition++;
        if(currentPosition >= images.size()){
            reset();
            // if can move: move
            if((face == Direction.LEFT && enemyBattleLine+attackDistance < front) || (face == Direction.RIGHT && front+attackDistance < enemyBattleLine)){
                next = unit.getState("move");
            }else if(unit.getCurrentAttackCd() == 0){// else if can attack: attack
                next = unit.getState("attack");
            }else{ // else idle
                next = unit.getState("idle");
            }
            next.update();
            unit.setState(next);
        }
    }
}
