import java.util.HashMap;
import java.util.Map;

public class summonCDHandler {

    private Map<SpriteCreator, Integer> coolDowns;

    public summonCDHandler(Map<SpriteCreator, Integer> CDs) {
        this.coolDowns = CDs;
    }

    public void update() {
        Map<SpriteCreator, Integer> newCDs = new HashMap<>();
        for(Map.Entry<SpriteCreator, Integer> set : this.coolDowns.entrySet()) {
            Integer cd = set.getValue();
            if(cd > 0) {
                cd -= 1;
            }
            newCDs.put(set.getKey(), cd);
        }
        this.coolDowns = newCDs;
        return;
    }

    public void startCD(SpriteCreator spriteCreator) {
        this.coolDowns.put(spriteCreator, 350);
        return;
    }

    public void getCD(SpriteCreator spriteCreator) {
        return this.coolDowns.get(spriteCreator);
    }
}
