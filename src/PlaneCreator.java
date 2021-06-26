import java.awt.*;
import java.util.Random;

public class PlaneCreator implements UnitCreator {

    @Override
    public Unit createUnit() {
        return new Plane();
    }

    @Override
    public Unit createUnit(Team team) {
        //System.out.println("=============creating ninja.....");
        Plane newPlane = new Plane();
        newPlane.setFace(team.direction);
        newPlane.setTeam(team);
        int width = newPlane.getWidth();
        int height = newPlane.getHeight();
        newPlane.setRange(getPlaneRange(team, width, height));
        return newPlane;
    }

    @Override
    public int getCost(){
        return 300;
    }

    private Rectangle getPlaneRange(Team team, int width, int height) {
        int x = (int)team.tower.getRange().getLocation().getX();
        int y = (int)team.tower.getRange().getLocation().getY() + team.tower.getHeight() - height;
        int dither = team.random.nextInt(16) - 8;

        return new Rectangle(new Point(x, y+dither-200), new Dimension(width, height));
    }
}
