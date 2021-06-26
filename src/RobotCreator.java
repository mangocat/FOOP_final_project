import java.awt.*;
import java.util.Random;

public class RobotCreator implements UnitCreator {

    @Override
    public Unit createUnit() {
        return new Robot();
    }

    @Override
    public Unit createUnit(Team team) {
        // System.out.println("=============creating ninja.....");
        Robot newRobot = new Robot();
        newRobot.setFace(team.direction);
        newRobot.setTeam(team);
        int width = newRobot.getWidth();
        int height = newRobot.getHeight();
        newRobot.setRange(getRobotRange(team, width, height));
        return newRobot;
    }

    @Override
    public int getCost() {
        return 100;
    }

    private Rectangle getRobotRange(Team team, int width, int height) {
        int x = (int) team.tower.getRange().getLocation().getX();
        int y = (int) team.tower.getRange().getLocation().getY() + team.tower.getHeight() - height;
        int dither = team.random.nextInt(16) - 8;

        return new Rectangle(new Point(x, y + dither), new Dimension(width, height));
    }
}
