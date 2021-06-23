import java.awt.*;
import java.util.Collection;

public class AttackState extends State{
    public AttackState(Unit u, String spriteType){
        super(u, "attack", ImageReader.read("assets/" + spriteType + "/attack"));
    }

    @Override
    public void update() {
        if(!unit.isAlive()){
            State dead = unit.getState("dead");
            dead.update();
            unit.setState(dead);
            return;
        }
        currentPosition++;
        if(currentPosition==0){
            // ready to attack, check if any sprite in range for attack, if not change to idle state
            Collection<Sprite> attackableUnit = unit.getTeam().getWorld().getSprites(unit.face, unit.location, unit.attackDistance);
            if(attackableUnit==null){ // no enemy in boundary
                reset();
                State next = unit.getState("idle");
                next.update();
                unit.setState(next);
                return;
            }
        } else if(currentPosition >= images.size()){
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

    @Override
    public void doAction() {
        Collection<Sprite> attackableUnit = unit.getTeam().getWorld().getSprites(unit.face, unit.location, unit.attackDistance);
        Sprite targetSprite;
        int minDistance = 10000;
        for(Sprite sprite : attackableUnit){
            if(Math.abs(sprite.getLocation().x - unit.location.x)<minDistance){
                targetSprite = sprite;
                minDistance = Math.abs(sprite.getLocation().x - unit.location.x);
            }
        }
        unit.setCurrentAttackCd(unit.originAttackCd); // set cd to max value
        targetSprite.takeDamage(unit.damage);
    }
}
