import java.awt.*;
import java.lang.Math;
import java.util.Collection;

public class Tower extends Sprite{
    private int hp;
    private int attackDistance;
    public static final int damage = 10;
    private Point location;
    public static final int maxAttackCd = 500;
    private int currentAttackCd;

    public Tower(int hp, int attackDistance, Point location){
        this.hp = hp;
        this.attackDistance = attackDistance;
        this.location = location;
        this.currentAttackCd = 0;
        // set range
    }

    @Override
    public void update() {
        currentAttackCd -= 1;
        if(currentAttackCd <= 0){
            Collection<Sprite> attackableUnit = team.getWorld().getSprites(face, location, attackDistance);
            for(Sprite sprite : attackableUnit){
                sprite.takeDamage(damage);
            }
            currentAttackCd = maxAttackCd; // set cd to max value
        }
    }

    @Override
    public void render(Graphics g) {}
}
