import java.awt.*;
import java.util.Collection;

public class AttackState extends State{
    public AttackState(Unit u, String spriteType, double scale, StateHandler stateHandler){
        super(u, "attack", ImageReader.read("assets/" + spriteType + "/attack", scale), stateHandler);
    }

/*    @Override
    public void update() {
        currentPosition++;
        if (currentPosition >= images.size()) {
            reset();
            currentPosition++;
            State next = stateHandler.nextState(this);
            //if (unit.team instanceof Human) System.out.println("============attack next: " + next);
            if (!(next instanceof AttackState)) {
                unit.setState(next);
                next.update();
            }
        }
//        if(!unit.isAlive()){
//            State dead = unit.getState("dead");
//            dead.update();
//            unit.setState(dead);
//            return;
//        }
//        currentPosition++;
//        if(currentPosition==4){
//            // ready to attack, check if any sprite in range for attack, if not change to idle state
//            Collection<Sprite> attackableUnit = unit.getTeam().getWorld().getSprites(unit, unit.getFace(), unit.getAttackDistance());
//            if(attackableUnit.isEmpty()){ // no enemy in boundary
//                // System.out.println("No enemy found!");
//                State next = unit.getState("idle");
//                next.update();
//                unit.setState(next);
//                return;
//            }
//        } else if(currentPosition >= images.size()){
//            reset();
//            // need enemyTeam battleLine
//            // if can move: move
//            Direction face = unit.getFace();
//            Rectangle range = unit.getFront();
//            int enemyBattleLine = unit.getEnemyBattleLine();
//            int attackDistance = unit.getAttackDistance();
//            int front = unit.getFront();
//            State next;
//            // int moveDistance;
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
    }*/

    @Override
    public boolean remains(State nextState) {
        return nextState instanceof AttackState;
    }

    @Override
    public void doAction() {
        if(currentPosition!=4){
            return;
        }
        Collection<Sprite> attackableUnit = unit.getTeam().getWorld().getSprites(unit, unit.getFace(), unit.getAttackDistance());
        //System.out.printf("attackable unit size : %d\n", attackableUnit.size());
        Sprite targetSprite = null;
        int minDistance = 10000;
        for(Sprite sprite : attackableUnit){
            if(Math.abs(sprite.getFront() - unit.getFront())<minDistance){
                targetSprite = sprite;
                minDistance = Math.abs(sprite.getFront() - unit.getFront());
            }
        }
        unit.setCurrentAttackCd(unit.getOriginAttackCd()); // set cd to max value
        if(targetSprite != null)
            targetSprite.takeDamage(unit.getDamage());
    }
}
