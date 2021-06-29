package model;

import utils.ButtonHandler;
import view.Button;
import utils.Direction;
import creator.UnitCreator;

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
        this.getIncomePeriod = 0.2;
        this.reduceCDPeriod = 0.2;
    }

    public void setButtons(List<Button> buttons) {
        initButtonHandler(buttons);
    }

    private void initButtonHandler(List<Button> buttons) {
        Map<UnitCreator, Button> ucToButtons = new LinkedHashMap<>(); 
        Map<UnitCreator, String> ucToStrings = new LinkedHashMap<>(); 
        Iterator<Button> buttonsIt = buttons.iterator();

        for (Map.Entry<String, UnitCreator> entry : this.unitCreators.entrySet()) {
            ucToButtons.put(entry.getValue(), buttonsIt.next());
            ucToStrings.put(entry.getValue(), entry.getKey());
        }

        this.buttonHandler = new ButtonHandler(this, ucToButtons, ucToStrings, buttons.get(buttons.size() - 1));
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
        g.setColor(Color.BLACK);  
        g.drawString("Level: " + this.level, 850, 30);
        g.drawString("Money: " + this.getMoney(), 850, 60);
    }

    @Override
    public void createSprite(String spriteName) {
        super.createSprite(spriteName);
        buttonHandler.setDisable(this.unitCreators.get(spriteName));
    }
}
