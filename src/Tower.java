import java.awt.*;
import java.lang.Math;
import java.util.Collection;

public class Tower extends Sprite{
    private int hp;
    private int attackDistance;
    public static final int damage = 10;
    // private Point location;
    public static final int maxAttackCd = 500;
    private int currentAttackCd;
    private final Image image;

    public Tower(int hp, int attackDistance, Point location){
        this.hp = hp;
        this.attackDistance = attackDistance;
        this.location = location;
        this.currentAttackCd = 0;
        this.width = 50;
        // set range
        image = ImageReader.read("assets/tower", width).get(0);
        height = image.getHeight(null);
        // need location to set x and y
        setRange(new Rectangle((int)location.getX(), (int)location.getY(), width, height));
    }

    @Override
    public void update() {
        currentAttackCd -= 1;
        if(currentAttackCd <= 0){
            Collection<Sprite> attackableUnit = team.getWorld().getSprites(this, face, attackDistance);
            for(Sprite sprite : attackableUnit){
                sprite.takeDamage(damage);
            }
            currentAttackCd = maxAttackCd; // set cd to max value
        }
    }

    @Override
    public void render(Graphics g) {
        if(face == Direction.LEFT){
            g.drawImage(image, range.x + range.width, range.y, -range.width, range.height, null);
        }else{
            g.drawImage(image, range.x, range.y, range.width, range.height, null);
        }
    }
}
