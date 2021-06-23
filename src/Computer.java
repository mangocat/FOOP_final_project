import java.util.Random;
import java.util.Map;
import java.awt.Point;

public class Computer extends Team {
    private Random random;
    private int numChoices;
    private int nextTarget;

    public Computer(World world, Map<String, UnitCreator> unitCreators) {
        super(world, unitCreators);
        this.direction = Direction.RIGHT;
        this.setTower(new Point(50, 250));
        this.updateBattleLine();
        this.random = new Random();
        this.numChoices = this.unitCreators.size() + 1; 
        this.nextTarget = this.chooseTarget();
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
        if(this.nextTarget == this.numChoices - 1) {
            if(this.money >= this.levelCost) {
                this.levelUp();
                this.nextTarget = this.chooseTarget();
            }
        }else {
            UnitCreator target = this.unitCreators.values().get(nextTarget);
            if((this.getCD(target) == 0) && (this.money >= target.getCost())) {
                this.createSprite(target);
                this.nextTarget = this.chooseTarget();
            }
        }
        return;
    }
}
