import java.util.LinkedHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.awt.*;

public class Human extends Team {
    private ButtonHandler buttonHandler;

    public Human(Map<String, UnitCreator> unitCreators) {
        super(unitCreators);
        this.direction = Direction.LEFT;
        this.setTower(new Point(850, 250));
        this.updateBattleLine();
        // this.initButtonHandler(buttons);
    }

    public void setButtons(List<Button> buttons){initButtonHandler(buttons);}

    private void initButtonHandler(List<Button> buttons) {
        Map<UnitCreator, Button> ucToButtons = new LinkedHashMap<>(); 
        Map<UnitCreator, String> ucToStrings = new LinkedHashMap<>(); 
        Iterator<Button> buttonsIt = buttons.iterator();
        for(Map.Entry<String, UnitCreator> entry : this.unitCreators.entrySet()) {
            ucToButtons.put(entry.getValue(), buttonsIt.next());
            ucToStrings.put(entry.getValue(), entry.getKey());
        }
        this.buttonHandler = new ButtonHandler(this, ucToButtons, ucToStrings, buttons.get(buttons.size()-1));
    }

    @Override
    public void update(int enemyBattleLine) {
        super.update(enemyBattleLine);
        buttonHandler.update();
        return;
    }   
    
    @Override
    public void render(Graphics g) {
        super.render(g);
        g.drawString("Level: "+this.level, 850, 30);
        g.drawString("Money: "+this.getMoney(), 850, 60);
    }

    // protected void updateBattleLine() {
    //     this.battleLine = this.tower.getFront();
    //     for(Sprite s : this.units) {
    //         int frontX = s.getFront();
    //         if(frontX > this.battleLine) {
    //             this.battleLine = frontX;
    //         }
    //     }
    //     return;
    // }

    @Override
    public void createSprite(String spriteName) {
        super.createSprite(spriteName);
        buttonHandler.setDisable(this.unitCreators.get(spriteName));
    }
}
