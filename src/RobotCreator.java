import java.awt.*;
import javax.imageio.ImageIO;
import java.nio.file.*;
import java.io.IOException;

public class RobotCreator implements UnitCreator {
    static final int ROBOT_HEIGHT = 100;
    private final int robotWidth;
    private final double robotScale;

    public RobotCreator(){
        Image sample;
        try{
            Path samplePath = Paths.get("assets/robot/idle/0.png");
            sample = ImageIO.read(samplePath.toFile());
        }catch(IOException e){
            throw new RuntimeException();
        }
        int originalHeight = sample.getHeight(null);
        int originalWidth = sample.getWidth(null);
        this.robotScale = ((double)ROBOT_HEIGHT)/originalHeight;
        this.robotWidth = (int)(robotScale*originalWidth);
    }

    @Override
    public Unit createUnit() {
        return new Robot(robotScale);
    }

    @Override
    public Unit createUnit(Team team) {
        // System.out.println("=============creating ninja.....");
        Robot newRobot = new Robot(robotScale);
        newRobot.setFace(team.direction);
        newRobot.setTeam(team);
        newRobot.setRange(getRobotRange(team, robotWidth, ROBOT_HEIGHT));
        return newRobot;
    }

    @Override
    public int getCost() {
        return 100;
    }

    private Rectangle getRobotRange(Team team, int width, int height) {
        int x = (team.direction == Direction.LEFT)? (int)team.tower.getFront() : (int)team.tower.getFront() - width;
        int y = (int) team.tower.getRange().getLocation().getY() + team.tower.getHeight() - height;
        int dither = team.random.nextInt(16) - 8;

        return new Rectangle(new Point(x, y + dither), new Dimension(width, height));
    }
}
