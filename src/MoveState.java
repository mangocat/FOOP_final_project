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
        int front = unit.getFront();
        int moveDistance = Math.min(unit.getMovementSpeed(), Math.abs(front-enemyBattleLine));
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

    @Override
    public void update(){
        // check if the unit is dead.
        if(!unit.isAlive()){
            State dead = unit.getState("dead");
            dead.update();
            unit.setState(dead);
            return;
        }
        currentPosition++;
        if(currentPosition >= images.size()){
            reset();
            // need enemyTeam battleLine
            // if can move: move
            Direction face = unit.getFace();
            Rectangle range = unit.getRange();
            int enemyBattleLine = unit.getEnemyBattleLine();
            int front = unit.getFront();
            State next;
            // int moveDistance;
            if((face == Direction.LEFT && enemyBattleLine < front) || (face == Direction.RIGHT && front < enemyBattleLine)){
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
