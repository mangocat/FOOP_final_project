import java.awt.*;
import javax.imageio.ImageIO;
import java.nio.file.*;
import java.io.IOException;

public class PlaneCreator implements UnitCreator {
    static final int PLANE_HEIGHT = 125;
    private final int planeWidth;
    private final double planeScale;

    public PlaneCreator(){
        Image sample;
        try{
            Path samplePath = Paths.get("assets/plane/idle/0.png");
            sample = ImageIO.read(samplePath.toFile());
        }catch(IOException e){
            throw new RuntimeException();
        }
        int originalHeight = sample.getHeight(null);
        int originalWidth = sample.getWidth(null);
        this.planeScale = ((double)PLANE_HEIGHT)/originalHeight;
        this.planeWidth = (int)(planeScale*originalWidth);
    }

    @Override
    public Unit createUnit() {
        return new Plane(planeScale);
    }

    @Override
    public Unit createUnit(Team team) {
        //System.out.println("=============creating ninja.....");
        Plane newPlane = new Plane(planeScale);
        newPlane.setFace(team.direction);
        newPlane.setTeam(team);
        newPlane.setRange(getPlaneRange(team, planeWidth, PLANE_HEIGHT));
        return newPlane;
    }

    @Override
    public int getCost(){
        return 300;
    }

    private Rectangle getPlaneRange(Team team, int width, int height) {
        int x = (team.direction == Direction.LEFT)? (int)team.tower.getFront() : (int)team.tower.getFront() - width;
        int y = (int)team.tower.getRange().getLocation().getY() + team.tower.getHeight() - height;
        int dither = team.random.nextInt(16) - 8;

        return new Rectangle(new Point(x, y+dither-200), new Dimension(width, height));
    }
}
