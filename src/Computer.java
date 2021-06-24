import java.util.Random;
import java.util.Iterator;
import java.util.Map;
import java.awt.Point;

public class Computer extends Team {
    private Random random;
    private int numChoices;
    private int nextTarget;

    public Computer(Map<String, UnitCreator> unitCreators) {
        super(unitCreators);
        this.direction = Direction.RIGHT;
        this.setTower(new Point(50, 250)); // set face
        this.updateBattleLine();
        this.random = new Random();
        this.numChoices = this.unitCreators.size() + 1; 
        this.nextTarget = 0;
    }

    private int chooseTarget() {
        return random.nextInt(numChoices);
    }

    @Override
    public void update(int enemyBattleLine) {
        super.update(enemyBattleLine);
        this.tryAct();
        return;
    }   

    private void tryAct() {
        Boolean done = (this.nextTarget == this.numChoices - 1)? this.tryLevelUp() : this.trySummon();
        if(done) {
            this.nextTarget = this.chooseTarget();
        }
        return;
    }

    private boolean tryLevelUp() {        
        if(this.money < this.levelCost) {
            return false;
        }
        this.levelUp();
        return true;
    }

    private boolean trySummon() {
        UnitCreator target = (UnitCreator)this.unitCreators.values().toArray()[nextTarget];
        if((this.getCD(target) > 0) || (this.money < target.getCost())){
            return false;
        }
        this.createSprite(target);
        return true;
    }
}
