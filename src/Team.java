import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.Graphics;

public abstract class Team {
    protected World world;
    protected List<Sprite> units = new ArrayList<>();
    protected Tower tower;
    protected Direction direction;
    protected int battleLine;
    protected int money;
    protected int level;
    protected int levelCost;
    protected Map<String, SpriteCreator> spriteCreators;
    protected summonCDHandler cdHandler;

    public Team(World world, Map<String, SpriteCreator> spriteCreators) {
        this.world = world;
        this.money = 0;
        this.level = 1;
        this.levelCost = this.getLevelUpCost();
        this.spriteCreators = spriteCreators;
        this.initCD();
        // spriteCreators.put("Ninja", new NinjaCreator());
    }

    private void initCD() {
        Map<SpriteCreator, Integer> CDs = new HashMap<>();
        for(SpriteCreator sc : this.spriteCreators.values()) {
            CDs.put(sc, 0);
        }
        this.cdHandler = new summonCDHandler(CDs); 
    }

    public void update(Team enemyTeam) {
        this.money += Level.getIncome(this.level);
        for(Sprite s : this.units) {
            s.update(enemyTeam);
        }
        this.updateBattleLine();
        this.cdHandler.update();
    }

    private void updateBattleLine() {
        this.battleLine = this.tower.getFront().getX();
        for(Sprite s : this.units) {
            int frontX = s.getFront.getX();
            if(frontX > this.battleLine) {
                this.battleLine = frontX;
            }
        }
        return;
    }

    protected void setTower() {
        this.tower = new Tower(this);
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
        SpriteCreator spriteCreator = this.spriteCreators.get(spriteName);
        this.money -= spriteCreator.getCost();
        Sprite newSprite = spriteCreator.createSprite();
        this.cdHandler.startCD(spriteCreator);
        this.addSprite(newSprite);
        return;
    }

    public void createSprite(SpriteCreator spriteCreator) {
        this.money -= spriteCreator.getCost();
        Sprite newSprite = spriteCreator.createSprite();
        this.cdHandler.startCD(spriteCreator);
        this.addSprite(newSprite);
        return;
    }

    public int getBattleLine() {
        return this.battleLine;
    }

    public void levelUp() {
        this.money -= this.levelCost;
        this.level += 1;
        this.levelCost = this.getLevelUpCost(this.level);
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

    public int getCD(SpriteCreator sc) {
        return this.cdHandler.getCD(sc);
    }

    public int getMoney() {
        return this.money;
    }
}