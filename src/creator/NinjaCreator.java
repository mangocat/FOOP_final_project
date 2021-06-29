package creator;

import sprite.Ninja;
import model.Team;
import utils.Direction;
import sprite.Unit;

import java.awt.*;
import javax.imageio.ImageIO;
import java.nio.file.*;
import java.io.IOException;


public class NinjaCreator implements UnitCreator {
    static final int NINJA_HEIGHT = 75;
    private final int ninjaWidth;
    private final double ninjaScale;

    public NinjaCreator() {
        Image sample;

        try {
            Path samplePath = Paths.get("assets/ninja/idle/0.png");
            sample = ImageIO.read(samplePath.toFile());
        } catch (IOException e) {
            throw new RuntimeException();
        }

        int originalHeight = sample.getHeight(null);
        int originalWidth = sample.getWidth(null);
        this.ninjaScale = ((double)NINJA_HEIGHT) / originalHeight;
        this.ninjaWidth = (int)(ninjaScale * originalWidth);
    }


    @Override
    public Unit createUnit() {
        return new Ninja(ninjaScale);
    }

    @Override
    public Unit createUnit(Team team) {
        Ninja newNinja = new Ninja(ninjaScale);
        newNinja.setFace(team.getDirection());
        newNinja.setTeam(team);
        newNinja.setRange(getNinjaRange(team, ninjaWidth, NINJA_HEIGHT));
        return newNinja;
    }

    @Override
    public int getCost() {
        return 50;
    }

    private Rectangle getNinjaRange(Team team, int width, int height) {
        int x = (team.getDirection() == Direction.LEFT) ? team.getTower().getFront() : team.getTower().getFront() - width;
        int y = (int)team.getTower().getRange().getLocation().getY() + team.getTower().getHeight() - height;
        int dither = team.randomInt(16) - 8;
        return new Rectangle(new Point(x, y + dither), new Dimension(width, height));
    }
}
