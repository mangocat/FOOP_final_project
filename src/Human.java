import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.*;

public class Human extends Team {
    private ButtonHandler buttonHandler;

    public Human(Map<String, UnitCreator> unitCreators) {
        super(unitCreators);
        this.direction = Direction.LEFT;
        this.setTower(new Point(450, 250));
        this.updateBattleLine();
        // this.initButtonHandler(buttons);
    }

    public void setButtons(List<Button> buttons){initButtonHandler(buttons);}

    private void initButtonHandler(List<Button> buttons) {
        Map<UnitCreator, Button> scToButtons = new HashMap<>(); 
        int cur = 0;       
        for(UnitCreator sc : this.unitCreators.values()) {
            scToButtons.put(sc, buttons.get(cur));
            cur += 1;
        }
        this.buttonHandler = new ButtonHandler(this, scToButtons);
    }

    @Override
    public void update(int enemyBattleLine) {
        super.update(enemyBattleLine);
        buttonHandler.update();
        return;
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

    @Override
    public void createSprite(String spriteName) {
        super.createSprite(spriteName);
        buttonHandler.setDisable(this.unitCreators.get(spriteName));
    }
}
