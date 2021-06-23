import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Human extends Team {
    private ButtonHandler buttonHandler;

    public Human(World world, Map<String, SpriteCreator> spriteCreators, List<Button> buttons) {
        super(world, spriteCreators);
        this.direction = Direction.LEFT;
        this.setTower();
        this.updateBattleLine();
        this.initButtonHandler(buttons);
    }

    private void initButtonHandler(List<Button> buttons) {
        Map<SpriteCreator, Button> scToButtons = new HashMap<>(); 
        int cur = 0;       
        for(SpriteCreator sc : this.spriteCreators.values()) {
            scToButtons.put(sc, buttons.get(cur));
            cur += 1;
        }
        this.buttonHandler = new ButtonHandler(this, scToButtons);
    }

    @Override
    public void update(Team enemyTeam) {
        super.update(enemyTeam);
        buttonHandler.update();
        return;
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

    @Override
    public void createSprite(String spriteName) {
        super.createSprite(spriteName);
        buttonHandler.setDisable(this.spriteCreators.get(spriteName));
    }
}
