import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.Graphics;
import java.awt.Point;

public abstract class Team {
    protected World world;
    protected List<Sprite> units = new ArrayList<>();
    protected Tower tower;
    protected Direction direction;
    protected int battleLine;
    protected int enemyBattleLine;
    protected int money;
    protected int level;
    protected int levelCost;
    protected Map<String, UnitCreator> unitCreators;
    protected summonCDHandler cdHandler;

    public Team(Map<String, UnitCreator> unitCreators) {
        this.money = 0;
        this.level = 1;
        this.levelCost = this.getLevelUpCost();
        this.unitCreators = unitCreators;
        this.initCD();
        // unitCreators.put("Ninja", new NinjaCreator());
    }

    public void setWorld(World w){
        world = w;
    }

    private void initCD() {
        Map<UnitCreator, Integer> CDs = new HashMap<>();
        for(UnitCreator sc : this.unitCreators.values()) {
            CDs.put(sc, 0);
        }
        this.cdHandler = new summonCDHandler(CDs); 
    }

    public void update(int enemyBattleLine) {
        this.enemyBattleLine = enemyBattleLine;
        this.money += Level.getIncome(this.level);
        for(Sprite s : this.units) {
            s.update();
        }
        this.updateBattleLine();
        this.cdHandler.update();
    }

    protected void updateBattleLine() {
        this.battleLine = this.tower.getFront();
        for(Sprite s : this.units) {
            int frontX = s.getFront();
            if(frontX > this.battleLine) {
                this.battleLine = frontX;
            }
        }
        return;
    }

    protected void setTower(Point p) {
        this.tower = new Tower(1000, 200, p);
        this.tower.setFace(this.direction);
    }

    public void addSprite(Sprite newSprite) {
        newSprite.setFace(this.direction);
        newSprite.setLocation(this.tower.getLocation());
        this.units.add(newSprite);
        return;
    }

    public void removeSprite(Sprite rmSprite) {
        this.units.remove(rmSprite);
        return;
    }

    public void createSprite(String spriteName) {
        UnitCreator unitCreator = this.unitCreators.get(spriteName);
        this.money -= unitCreator.getCost();
        Sprite newSprite = unitCreator.createUnit();
        this.cdHandler.startCD(unitCreator);
        this.addSprite(newSprite);
        return;
    }

    public void createSprite(UnitCreator unitCreator) {
        this.money -= unitCreator.getCost();
        Sprite newSprite = unitCreator.createUnit();
        this.cdHandler.startCD(unitCreator);
        this.addSprite(newSprite);
        return;
    }

    public int getBattleLine() {
        return this.battleLine;
    }

    public int getEnemyBattleLine() {
        return this.enemyBattleLine;
    }

    public void levelUp() {
        this.money -= this.levelCost;
        this.level += 1;
        this.levelCost = this.getLevelUpCost();
        return;
    }

    public int getLevelUpCost() {
        return Level.getLevelCost(this.level);
    }

    public void render(Graphics g) {
        this.tower.render(g);
        for(Sprite s : this.units) {
            s.render(g);
        }
        return;
    }

    public boolean isLose() {
        return this.tower.isAlive();
    }

    public World getWorld() {
        return this.world;
    }

    public List<Sprite> getSprites() {
        return this.units;
    }

    public int getCD(UnitCreator sc) {
        return this.cdHandler.getCD(sc);
    }

    public int getMoney() {
        return this.money;
    }
}