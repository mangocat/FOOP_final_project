import java.util.List;
import java.awt.*;

public class State {
    Unit unit;
    final String name;
    final List<Image> images;
    int currentPosition;
    
    public State(Unit unit, String stateName, List<Image> stateImages){
        this.unit = unit;
        name = stateName;
        images = stateImages;
        currentPosition = -1;
    }
    public void reset(){
        currentPosition = -1;
    }
    public void doAction(){} // default: do nothing
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
            int enemyBattleLine = unit.getEnemyTeam().getBattleLine();
            int front = unit.getFront();
            State next;
            // int moveDistance;
            if((face == Direction.LEFT && enemyBattleLine < front) || (face == Direction.Right && front < enemyBattleLine)){
                next = unit.getState("move");
            }else if(unit.getAttackCd() == 0){// else if can attack: attack
                next = unit.getState("attack");
            }else{ // else idle
                next = unit.getState("idle");
            }
            next.update();
            unit.setState(next);
        }
    }
    public void render(Graphics g){
        Direction face = unit.getFace();
        Rectangle range = unit.getRange();
        if(face == Direction.LEFT){
            g.drawImage(images.get(currentPosition), range.x + range.width, range.y, -range.width, range.height, null);
        }else{
            g.drawImage(images.get(currentPosition), range.x, range.y, range.width, range.height, null);
        }
    }
    public boolean finished(){
        return currentPosition == images.size()-1;
    }
    public String toString(){
        return name;
    }
}
