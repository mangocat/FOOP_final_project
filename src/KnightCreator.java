import java.awt.*;
import javax.imageio.ImageIO;
import java.nio.file.*;
import java.io.IOException;

public class KnightCreator implements UnitCreator {
    static final int KNIGHT_HEIGHT = 125;
    private final int knightWidth;
    private final double knightScale;

    public KnightCreator(){
        Image sample;
        try{
            Path samplePath = Paths.get("assets/knight/idle/0.png");
            sample = ImageIO.read(samplePath.toFile());
        }catch(IOException e){
            throw new RuntimeException();
        }
        int originalHeight = sample.getHeight(null);
        int originalWidth = sample.getWidth(null);
        this.knightScale = ((double)KNIGHT_HEIGHT)/originalHeight;
        this.knightWidth = (int)(knightScale*originalWidth);
    }

    @Override
    public Unit createUnit() {
        return new Knight(knightScale);
    }

    @Override
    public Unit createUnit(Team team) {
        //System.out.println("=============creating ninja.....");
        Knight newKnight = new Knight(knightScale);
        newKnight.setFace(team.direction);
        newKnight.setTeam(team);
        newKnight.setRange(getKnightRange(team, knightWidth, KNIGHT_HEIGHT));
        return newKnight;
    }

    @Override
    public int getCost(){
        return 75;
    }

    private Rectangle getKnightRange(Team team, int width, int height) {
        int x = (team.direction == Direction.LEFT)? (int)team.tower.getFront() : (int)team.tower.getFront() - width;
        int y = (int)team.tower.getRange().getLocation().getY() + team.tower.getHeight() - height;
        int dither = team.random.nextInt(16) - 8;

        return new Rectangle(new Point(x, y+dither), new Dimension(width, height));
    }
}
