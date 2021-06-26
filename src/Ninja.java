import java.awt.*;

import javax.imageio.ImageIO;

import java.util.ArrayList;
import java.awt.Image;
import java.io.IOException;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.DirectoryStream;

public class Ninja extends Unit {
    static final int attackCd = 100; // attack every 30 updates
    static final int attackDistance = 10;
    static final int maxHp = 100;
    static final int speed = 3;
    static final int ninjaDamage = 20;

    public Ninja() {
        super(speed, attackDistance, maxHp, ninjaDamage, attackCd);
        this.height = 200;
        Image sample;
        try{
            Path samplePath = Paths.get("assets/ninja/idle/0.png");
            sample = ImageIO.read(samplePath.toFile());
        }catch(IOException e){
            throw new RuntimeException();
        }
        int originalHeight = sample.getHeight(null);
        int originalWidth = sample.getWidth(null);
        double scale = ((double)height)/originalHeight;
        width = (int)(originalWidth*scale);

        stateMap.put("attack", new AttackState(this, "ninja", scale));
        stateMap.put("idle", new IdleState(this, "ninja", scale));
        stateMap.put("move", new MoveState(this, "ninja", scale));
        stateMap.put("dead", new DeadState(this, "ninja", scale));
        currentState = stateMap.get("move");
    }
}
