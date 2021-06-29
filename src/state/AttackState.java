package state;

import utils.StateHandler;
import utils.ImageReader;
import sprite.Sprite;
import sprite.Unit;

import java.awt.*;
import java.util.Collection;

public class AttackState extends State {
    public AttackState(Unit u, String spriteType, double scale, StateHandler stateHandler) {
        super(u, "attack", ImageReader.read("assets/" + spriteType + "/attack", scale), stateHandler);
    }

    @Override
    public boolean remains(State nextState) {
        return nextState instanceof AttackState;
    }

    @Override
    public void doAction() {
        if (currentPosition != 4) {
            return;
        }

        Collection<Sprite> attackableUnit = unit.getTeam().getWorld().getSprites(unit, unit.getFace(), unit.getAttackDistance());
        Sprite targetSprite = null;
        int minDistance = 10000;

        for (Sprite sprite : attackableUnit)
            if (Math.abs(sprite.getFront() - unit.getFront()) < minDistance) {
                targetSprite = sprite;
                minDistance = Math.abs(sprite.getFront() - unit.getFront());
            }

        unit.setCurrentAttackCd(unit.getOriginAttackCd());

        if (targetSprite != null)
            targetSprite.takeDamage(unit.getDamage());
    }
}
