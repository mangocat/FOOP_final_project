import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.awt.*;

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
    protected SummonCoolDownHandler cdHandler;
    protected Random random;
    protected double updatePeriod = 0.1;
    protected double updateTime;

    public Team(Map<String, UnitCreator> unitCreators) {
        this.money = 0;
        this.level = 1;
        this.levelCost = this.getLevelUpCost();
        this.unitCreators = unitCreators;
        this.initCD();
        this.random = new Random();
        this.updateTime = 0;
        // unitCreators.put("Ninja", new NinjaCreator());
    }

    public void setWorld(World w){
        world = w;
    }

    private void initCD() {
        Map<UnitCreator, Integer> CDs = new LinkedHashMap<>();
        for(UnitCreator sc : this.unitCreators.values()) {
            CDs.put(sc, 0);
        }
        this.cdHandler = new SummonCoolDownHandler(CDs); 
    }

    public void update(int enemyBattleLine) {
        this.updateTime += 15.0/1000;
        if(this.updateTime >= this.updatePeriod) {
            this.updateTime -= this.updatePeriod;            
            this.money += Level.getIncome(this.level);
            this.cdHandler.update();
        }
        this.enemyBattleLine = enemyBattleLine;
        for(int i = 0; i < this.units.size(); i++) {
            this.units.get(i).update();
        }
        this.updateBattleLine();
        this.cdHandler.update();
        this.tower.update();
    }

    protected void updateBattleLine() {
        this.battleLine = this.tower.getFront();
        for(Sprite s : this.units) {
            int frontX = s.getFront();
            Direction face = tower.getFace();
            if(face == Direction.RIGHT && frontX > this.battleLine) {
                this.battleLine = frontX;
                continue;
            }
            if(face == Direction.LEFT && frontX < this.battleLine){
                this.battleLine = frontX;
            }
        }
        return;
    }

    protected void setTower(Point p) {
        this.tower = new Tower(1000, 200, p);
        this.tower.setFace(this.direction);
        this.tower.setTeam(this);
    }

    public void addSprite(Sprite newSprite) {
//        newSprite.setFace(this.direction);
//        // newSprite.setLocation(this.tower.getRange().getLocation());
//        newSprite.setTeam(this);
//
//        int x = (int)this.tower.getRange().getLocation().getX();
//        int y = (int)this.tower.getRange().getLocation().getY() + this.tower.getHeight() - newSprite.getHeight();
//        int dither = this.random.nextInt(16) - 8;
//        newSprite.setRange(new Rectangle(new Point(x, y+dither), new Dimension(newSprite.getWidth(), newSprite.getHeight())));
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
        Sprite newSprite = unitCreator.createUnit(this);
        this.cdHandler.startCD(unitCreator);
        this.addSprite(newSprite);
        return;
    }

    public void createSprite(UnitCreator unitCreator) {
        this.money -= unitCreator.getCost();
        Sprite newSprite = unitCreator.createUnit(this);
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
        for(int i = 0; i < this.units.size(); i++) {
            this.units.get(i).render(g);
        }
        return;
    }

    public boolean isLose() {
        return !this.tower.isAlive();
    }

    public World getWorld() {
        return this.world;
    }

    public List<Sprite> getSprites() {
        List<Sprite> allSprites = new ArrayList<>();
        allSprites.addAll(this.units);
        allSprites.add(this.tower);
        return allSprites;
    }

    public int getCD(UnitCreator sc) {
        return this.cdHandler.getCD(sc);
    }

    public int getMoney() {
        return this.money;
    }

    public int getLevelCost() {
        return this.levelCost;
    }
}