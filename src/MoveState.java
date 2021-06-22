public class MoveState extends State{
    public MoveState(Sprite s, String spriteType){
        super(s, "move", ImageReader.read("assets/" + spriteType + "/move"));
    }
    @Override
    public void doAction(){
        Direction face = sprite.getFace();
        Rectangle range = sprite.getRange();
        int enemyBattleLine = sprite.getEnemyTeam().getBattleLine();
        int front = sprite.getFront();
        int moveDistance = Math.min(sprite.getMovementSpeed(), Math.abs(front-enemyBattleLine));
        // notify sprite position change
        if(face == Direction.LEFT){
            range.x -= moveDistance;
            front -= moveDistance;
        }else{
            range.x += moveDistance;
            front += moveDistance;
        }
        sprite.setRange(range);
        sprite.setFront(front);
    }
    @Override
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
}
