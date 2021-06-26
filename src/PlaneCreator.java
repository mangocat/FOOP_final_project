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
        newPlane.setRange(getPlaneRange(team));
        return newPlane;
    }

    @Override
    public int getCost(){
        return 500;
    }

    private Rectangle getPlaneRange(Team team) {
        int x = (int)team.tower.getRange().getLocation().getX();
        int y = (int)team.tower.getRange().getLocation().getY() + team.tower.getHeight() - Plane.PLANE_HEIGHT;
        int dither = team.random.nextInt(16) - 8;

        return new Rectangle(new Point(x, y+dither-200), new Dimension(Plane.PLANE_WIDTH, Plane.PLANE_HEIGHT));
    }
}
