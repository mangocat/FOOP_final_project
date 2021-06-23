import java.util.Random;

public class Computer extends Team {
    private Random random;
    private int numChoices;
    private int nextTarget;

    public Computer(World world, Map<String, SpriteCreator> spriteCreators) {
        super(world, spriteCreators);
        this.random = new Random();
        this.numChoices = this.spriteCreators.size() + 1; 
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
            SpriteCreator target = this.spriteCreators.values().get(nextTarget);
            if((this.getCD(target) == 0) && (this.money >= target.getCost())) {
                this.createSprite(target);
                this.nextTarget = this.chooseTarget();
            }
        }
        return;
    }
}
