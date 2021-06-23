import java.awt.*;

public class IdleState extends State{
    public IdleState(Unit u, String spriteType, int preferredWidth) {
        super(u, "idle", ImageReader.read("assets/" + spriteType + "/idle", preferredWidth));
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
            // if the range is enough, try to attack
            if(unit.getCurrentAttackCd() == 0){
                reset();
                next = unit.getState("attack");
                next.update();
                unit.setState(next);
                return;
            }
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
