import java.util.List;
import java.awt.*;

public class State {
    Sprite sprite;
    final String name;
    final List<Image> images;
    int currentPosition;
    
    public State(Sprite sprite, String stateName, List<Image> stateImages){
        this.sprite = sprite;
        name = stateName;
        images = stateImages;
        currentPosition = -1;
    }
    public void reset(){
        currentPosition = -1;
    }
    public void doAction(){} // default: do nothing
    public void update(){
        // check if the sprite is dead.
        if(!sprite.isAlive()){
            State dead = sprite.getState("dead");
            dead.update();
            sprite.setState(dead);
            return;
        }
        currentPosition++;
        if(currentPosition >= images.size()){
            reset();
            // need enemyTeam battleLine
            // if can move: move
            Direction face = sprite.getFace();
            Rectangle range = sprite.getRange();
            int enemyBattleLine = sprite.getEnemyTeam().getBattleLine();
            int front = sprite.getFront();
            State next;
            // int moveDistance;
            if((face == Direction.LEFT && enemyBattleLine < front) || (face == Direction.Right && front < enemyBattleLine)){
                next = sprite.getState("move");
            }else if(sprite.getAttackCd() == 0){// else if can attack: attack
                next = sprite.getState("attack");
            }else{ // else idle
                next = sprite.getState("idle");
            }
            next.update();
            sprite.setState(next);
        }
    }
    public void render(Graphics g){
        Direction face = sprite.getFace();
        Rectangle range = sprite.getRange();
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
