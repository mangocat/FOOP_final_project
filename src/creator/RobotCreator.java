package creator;

import sprite.Robot;
import model.Team;
import utils.Direction;
import sprite.Unit;

import java.awt.*;
import javax.imageio.ImageIO;
import java.nio.file.*;
import java.io.IOException;

public class RobotCreator implements UnitCreator {
    static final int ROBOT_HEIGHT = 100;
    private final int robotWidth;
    private final double robotScale;

    public RobotCreator() {
        Image sample;

        try {
            Path samplePath = Paths.get("assets/robot/idle/0.png");
            sample = ImageIO.read(samplePath.toFile());
        } catch (IOException e) {
            throw new RuntimeException();
        }

        int originalHeight = sample.getHeight(null);
        int originalWidth = sample.getWidth(null);
        this.robotScale = ((double)ROBOT_HEIGHT) / originalHeight;
        this.robotWidth = (int)(robotScale * originalWidth);
    }

    @Override
    public Unit createUnit() {
        return new Robot(robotScale);
    }

    @Override
    public Unit createUnit(Team team) {
        Robot newRobot = new Robot(robotScale);
        newRobot.setFace(team.getDirection());
        newRobot.setTeam(team);
        newRobot.setRange(getRobotRange(team, robotWidth, ROBOT_HEIGHT));
        return newRobot;
    }

    @Override
    public int getCost() {
        return 100;
    }

    private Rectangle getRobotRange(Team team, int width, int height) {
        int x = (team.getDirection() == Direction.LEFT) ? (int)team.getTower().getFront() : (int)team.getTower().getFront() - width;
        int y = (int) team.getTower().getRange().getLocation().getY() + team.getTower().getHeight() - height;
        int dither = team.randomInt(16) - 8;
        return new Rectangle(new Point(x, y + dither), new Dimension(width, height));
    }
}
