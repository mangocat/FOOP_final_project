package sprite;

import utils.Direction;

import java.awt.*;
import java.util.Collection;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Tower extends Sprite {
    private TowerHpBar hpBar;
    private int maxHp = 1000;
    public static final int damage = 50;
    public static final int maxAttackCd = 500;
    private int currentAttackCd;
    private final Image image;

    public Tower(Point location) {
        this.hp = maxHp;
        this.location = location;
        this.currentAttackCd = 0;
        this.height = 200;
        this.attackDistance = 300;
        Image tmpImage;

        try {
            Path imagePath = Paths.get("assets/tower/0.png");
            File imageFile = imagePath.toFile();
            tmpImage = ImageIO.read(imageFile);
        } catch (IOException e){
            throw new RuntimeException();
        }

        int originalHeight = tmpImage.getHeight(null);
        double scale = ((double)height) / originalHeight;
        this.width = (int)(tmpImage.getWidth(null) * scale);
        image = tmpImage.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        setRange(new Rectangle((int)location.getX(), (int)location.getY(), width, height));
        hpBar = new TowerHpBar(this);
    }

    public int getCurHp() {
        return hp;
    }

    @Override
    public void update() {
        currentAttackCd -= 1;

        if (currentAttackCd <= 0) {
            Collection<Sprite> attackableUnit = team.getWorld().getSprites(this, face, attackDistance);

            if (attackableUnit.size() > 0) {
                currentAttackCd = maxAttackCd;
            }

            for (Sprite sprite : attackableUnit) {
                sprite.takeDamage(damage);
            }
        }

        hpBar.update();
    }

    @Override
    public void render(Graphics g) {
        hpBar.render(g);

        if (face == Direction.LEFT) {
            g.drawImage(image, range.x + range.width, range.y, -range.width, range.height, null);
        } else {
            g.drawImage(image, range.x, range.y, range.width, range.height, null);
        }

        int x = (this.face == Direction.RIGHT) ? this.getFront() : this.getRange().x - this.attackDistance; 
        int y = this.getRange().y + this.height - 5;

        if (currentAttackCd >= maxAttackCd*0.97) {
            g.setColor(Color.RED);  
            g.fillRect(x, y, this.attackDistance, 5);
        } else {
            g.setColor(Color.YELLOW);  
            g.fillRect(x, y, this.attackDistance, 5);
        }
    }

    public void reset() {
        this.hp = this.maxHp;
    }
}
