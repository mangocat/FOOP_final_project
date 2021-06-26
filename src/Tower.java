import java.awt.*;
import java.util.Collection;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Tower extends Sprite{
    private TowerHpBar hpBar;
    private int attackDistance;
    public static final int damage = 50;
    public static final int maxAttackCd = 500;
    private int currentAttackCd;
    private final Image image;
    private int maxHp;

    public Tower(int hp, int attackDistance, Point location){
        this.hp = hp;
        this.maxHp = hp;
        this.attackDistance = attackDistance;
        this.location = location;
        this.currentAttackCd = 0;
        this.height = 200;
        Image tmpImage;
        try{
            Path imagePath = Paths.get("assets/tower/0.png");
            File imageFile = imagePath.toFile();
            tmpImage = ImageIO.read(imageFile);
        }catch(IOException e){
            throw new RuntimeException();
        }
        int originalHeight = tmpImage.getHeight(null);
        double scale = ((double)height)/originalHeight;
        this.width = (int)(tmpImage.getWidth(null)*scale);
        // set range
        image = tmpImage.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        
        // need location to set x and y
        setRange(new Rectangle((int)location.getX(), (int)location.getY(), width, height));

        // create towerHpBar
        hpBar = new TowerHpBar(this);
    }

    public int getCurHp(){
        return hp;
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
        hpBar.update();
    }

    @Override
    public void render(Graphics g) {
        hpBar.render(g);
        if(face == Direction.LEFT){
            g.drawImage(image, range.x + range.width, range.y, -range.width, range.height, null);
        }else{
            g.drawImage(image, range.x, range.y, range.width, range.height, null);
        }
    }

    public void reset(){
        this.hp = this.maxHp;
    }
}
