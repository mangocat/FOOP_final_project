package creator;

import sprite.Plane;
import model.Team;
import utils.Direction;
import sprite.Unit;

import java.awt.*;
import javax.imageio.ImageIO;
import java.nio.file.*;
import java.io.IOException;

public class PlaneCreator implements UnitCreator {
    static final int PLANE_HEIGHT = 125;
    private final int planeWidth;
    private final double planeScale;

    public PlaneCreator() {
        Image sample;

        try {
            Path samplePath = Paths.get("assets/plane/idle/0.png");
            sample = ImageIO.read(samplePath.toFile());
        } catch (IOException e) {
            throw new RuntimeException();
        }

        int originalHeight = sample.getHeight(null);
        int originalWidth = sample.getWidth(null);
        this.planeScale = ((double)PLANE_HEIGHT) / originalHeight;
        this.planeWidth = (int)(planeScale * originalWidth);
    }

    @Override
    public Unit createUnit() {
        return new Plane(planeScale);
    }

    @Override
    public Unit createUnit(Team team) {
        Plane newPlane = new Plane(planeScale);
        newPlane.setFace(team.getDirection());
        newPlane.setTeam(team);
        newPlane.setRange(getPlaneRange(team, planeWidth, PLANE_HEIGHT));
        return newPlane;
    }

    @Override
    public int getCost() {
        return 300;
    }

    private Rectangle getPlaneRange(Team team, int width, int height) {
        int x = (team.getDirection() == Direction.LEFT) ? (int)team.getTower().getFront() : (int)team.getTower().getFront() - width;
        int y = (int)team.getTower().getRange().getLocation().getY() + team.getTower().getHeight() - height;
        int dither = team.randomInt(16) - 8;
        return new Rectangle(new Point(x, y + dither - 200), new Dimension(width, height));
    }
}
