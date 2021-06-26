import java.util.HashMap;
import java.util.Map;

public class SummonCoolDownHandler {    
    private Map<UnitCreator, Integer> coolDowns;

    public SummonCoolDownHandler(Map<UnitCreator, Integer> CDs) {
        this.coolDowns = CDs;
    }

    public void update() {
        Map<UnitCreator, Integer> newCDs = new HashMap<>();
        for(Map.Entry<UnitCreator, Integer> set : this.coolDowns.entrySet()) {
            Integer cd = set.getValue();
            cd = (cd > 0)? cd-1 : cd;
            newCDs.put(set.getKey(), cd);
        }
        this.coolDowns = newCDs;
        return;
    }

    public void startCD(UnitCreator unitCreator) {
        this.coolDowns.put(unitCreator, unitCreator.getCost()*2);
        return;
    }

    public int getCD(UnitCreator unitCreator) {
        return this.coolDowns.get(unitCreator);
    }
}
