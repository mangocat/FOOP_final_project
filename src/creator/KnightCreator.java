package creator;

import sprite.Knight;
import model.Team;
import utils.Direction;
import sprite.Unit;

import java.awt.*;
import javax.imageio.ImageIO;
import java.nio.file.*;
import java.io.IOException;

public class KnightCreator implements UnitCreator {
    static final int KNIGHT_HEIGHT = 125;
    private final int knightWidth;
    private final double knightScale;

    public KnightCreator() {
        Image sample;

        try {
            Path samplePath = Paths.get("assets/knight/idle/0.png");
            sample = ImageIO.read(samplePath.toFile());
        } catch (IOException e) {
            throw new RuntimeException();
        }

        int originalHeight = sample.getHeight(null);
        int originalWidth = sample.getWidth(null);
        this.knightScale = ((double)KNIGHT_HEIGHT) / originalHeight;
        this.knightWidth = (int)(knightScale * originalWidth);
    }

    @Override
    public Unit createUnit() {
        return new Knight(knightScale);
    }

    @Override
    public Unit createUnit(Team team) {
        Knight newKnight = new Knight(knightScale);
        newKnight.setFace(team.getDirection());
        newKnight.setTeam(team);
        newKnight.setRange(getKnightRange(team, knightWidth, KNIGHT_HEIGHT));
        return newKnight;
    }

    @Override
    public int getCost() {
        return 75;
    }

    private Rectangle getKnightRange(Team team, int width, int height) {
        int x = (team.getDirection() == Direction.LEFT) ? (int)team.getTower().getFront() : (int)team.getTower().getFront() - width;
        int y = (int)team.getTower().getRange().getLocation().getY() + team.getTower().getHeight() - height;
        int dither = team.randomInt(16) - 8;
        return new Rectangle(new Point(x, y + dither), new Dimension(width, height));
    }
}
