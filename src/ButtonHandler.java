import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ButtonHandler {
    private Human human;
    private Map<SpriteCreator, Button> buttons;
    
    public ButtonHandler(Map<SpriteCreator, Button> buttons) {
        this.buttons = buttons;
    }

    public void update() {        
        for(SpriteCreator sc : this.buttons.keySet()) {
            Boolean isAble = true;
            if((this.human.getCD(sc) > 0) || (this.human.getMoney() < sc.getCost())) {
                isAble = false;
            }
            this.buttons.get(sc).setEnabled(isAble);
        }
    }

    public void setDisable(SpriteCreator sc) {
        this.buttons.get(sc).setEnabled(false);
        return;
    }
}
