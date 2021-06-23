import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.awt.*;

public class ButtonHandler {
    private Human human;
    private Map<UnitCreator, Button> buttons;
    
    public ButtonHandler(Human human, Map<UnitCreator, Button> buttons) {
        this.human = human;
        this.buttons = buttons;
    }

    public void update() {        
        for(UnitCreator sc : this.buttons.keySet()) {
            Boolean isAble = true;
            if((this.human.getCD(sc) > 0) || (this.human.getMoney() < sc.getCost())) {
                isAble = false;
            }
            this.buttons.get(sc).setEnabled(isAble);
        }
    }

    public void setDisable(UnitCreator sc) {
        this.buttons.get(sc).setEnabled(false);
        return;
    }
}
