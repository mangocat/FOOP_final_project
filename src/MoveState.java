import java.awt.*;

public class MoveState extends State{
    public MoveState(Unit u, String unitType, double scale, StateHandler stateHandler){
        super(u, "move", ImageReader.read("assets/" + unitType + "/move", scale), stateHandler);
    }
    @Override
    public void doAction(){
        //System.out.println("========================Doing action");

        Direction face = unit.getFace();
        Rectangle range = unit.getRange();
        //System.out.println("===========before: "  + range + "face: " + face);
        int enemyBattleLine = unit.getEnemyBattleLine();
        int attackDistance = unit.getAttackDistance();
        int front = unit.getFront();
        int moveDistance = Math.min(unit.getMovementSpeed(), Math.max(0, Math.abs(front-enemyBattleLine)-attackDistance));
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
        //System.out.println("===========after: "  + range  + "face: " + face);
    }
//     public void update(){
//         currentPosition++;
//         if (currentPosition >= images.size()) {
//             reset();
//             currentPosition++;
//             State next = stateHandler.nextState(this);
//             //if (unit.team instanceof Human) System.out.println("============move next: " + next);
//             if (!(next instanceof MoveState)) {
//                 unit.setState(next);
//                 next.update();
//             }
//         }
//         State next = stateHandler.nextState(this);
//         if (!(next instanceof MoveState)) {
//             unit.setState(next);
//             next.update();
//         }


// // step 1 , Check if unit dead
//        if(!unit.isAlive()){
//            State dead = unit.getState("dead");
//            dead.update();
//            unit.setState(dead);
//            return;
//        }

// // step 2 , determine if change from move to attack or idle 
//        Direction face = unit.getFace();
//        Rectangle range = unit.getRange();
//        int enemyBattleLine = unit.getEnemyBattleLine();
//        int attackDistance = unit.getAttackDistance();
//        int front = unit.getFront();
//        State next;
//        if((face == Direction.LEFT && enemyBattleLine+attackDistance >= front) || (face == Direction.RIGHT && front+attackDistance >= enemyBattleLine)){
//            reset();
//            // if the range is enough, try to stop and attack
//            if(unit.getCurrentAttackCd() <= 0){
//                next = unit.getState("attack");
//            }else{
//                next = unit.getState("idle");
//            }
//            next.update();
//            unit.setState(next);
//            return;
//        }

// // step 3 , currentPosition++
//        currentPosition++;

// // step 4 , determine if currentPosition exceeds image size and if change state 
//        if(currentPosition >= images.size()){
//            reset();
//            // if can move: move
//            if((face == Direction.LEFT && enemyBattleLine+attackDistance < front) || (face == Direction.RIGHT && front+attackDistance < enemyBattleLine)){
//                next = unit.getState("move");
//            }else if(unit.getCurrentAttackCd() <= 0){// else if can attack: attack
//                next = unit.getState("attack");
//            }else{ // else idle
//                next = unit.getState("idle");
//            }
//            next.update();
//            unit.setState(next);
//        }
//     }

    @Override
    public boolean remains(State nextState) {
        return nextState instanceof MoveState;
    }
}
