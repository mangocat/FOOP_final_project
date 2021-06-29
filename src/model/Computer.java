package model;

import creator.UnitCreator;
import utils.Direction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.awt.Point;

public class Computer extends Team {
    private int numChoices;
    private int nextTarget;

    public Computer(Map<String, UnitCreator> unitCreators) {
        super(unitCreators);
        this.direction = Direction.RIGHT;
        this.setTower(new Point(50, 250));
        this.updateBattleLine();
        this.numChoices = this.unitCreators.size() + 1; 
        this.nextTarget = 0;
        this.getIncomePeriod = 0.15;
        this.reduceCDPeriod = 0.15;
    }

    private int chooseTarget() {
        List<Double> weight = new ArrayList<Double>();

        for(UnitCreator uc : this.unitCreators.values()) {
            weight.add(1.0 / uc.getCost());
        }

        weight.add(1.0 / this.getLevelCost());
        
        for (int i = 1; i < weight.size(); i++) {
            weight.set(i, weight.get(i) + weight.get(i - 1));
        }

        double randomRes = this.random.nextDouble() * weight.get(weight.size() - 1);

        for (int i = 0; i < weight.size(); i++) {
            if(randomRes < weight.get(i)) {
                return i;
            }
        }

        return weight.size() - 1;
    }

    @Override
    public void update(int enemyBattleLine) {
        super.update(enemyBattleLine);
        this.tryAct();
        return;
    }   

    private void tryAct() {
        Boolean done = (this.nextTarget == this.numChoices - 1) ? this.tryLevelUp() : this.trySummon();

        if (done) {
            this.nextTarget = this.chooseTarget();
        }

        return;
    }

    private boolean tryLevelUp() {        
        if (this.money < this.levelCost) {
            return false;
        }

        this.levelUp();
        return true;
    }

    private boolean trySummon() {
        UnitCreator target = (UnitCreator)this.unitCreators.values().toArray()[nextTarget];

        if ((this.getCD(target) > 0) || (this.money < target.getCost())) {
            return false;
        }

        this.createSprite(target);
        return true;
    }
}
