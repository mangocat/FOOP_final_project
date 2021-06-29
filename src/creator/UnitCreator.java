package creator;

import model.Team;
import sprite.Unit;

public interface UnitCreator {
    public Unit createUnit();
    public Unit createUnit(Team team);
    public int getCost();
}
