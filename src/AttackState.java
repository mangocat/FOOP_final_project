import java.awt.*;
import java.util.Collection;

public class AttackState extends State{
    public AttackState(Unit u, String spriteType, int preferredWidth){
        super(u, "attack", ImageReader.read("assets/" + spriteType + "/attack", preferredWidth));
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
            Collection<Sprite> attackableUnit = unit.getTeam().getWorld().getSprites(unit.getFace(), unit.getLocation(), unit.getAttackDistance());
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
            int attackDistance = unit.getAttackDistance();
            State next;
            // int moveDistance;
            if((face == Direction.LEFT && enemyBattleLine+attackDistance < front) || (face == Direction.RIGHT && front+attackDistance < enemyBattleLine)){
                next = unit.getState("move");
            }else if(unit.getCurrentAttackCd() <= 0){// else if can attack: attack
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
        Collection<Sprite> attackableUnit = unit.getTeam().getWorld().getSprites(unit.getFace(), unit.getLocation(), unit.getAttackDistance());
        Sprite targetSprite = new Ninja();
        int minDistance = 10000;
        for(Sprite sprite : attackableUnit){
            if(Math.abs(sprite.getLocation().x - unit.getLocation().x)<minDistance){
                targetSprite = sprite;
                minDistance = Math.abs(sprite.getLocation().x - unit.getLocation().x);
            }
        }
        unit.setCurrentAttackCd(unit.getOriginAttackCd()); // set cd to max value
        targetSprite.takeDamage(unit.getDamage());
    }
}
