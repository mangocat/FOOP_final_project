import java.awt.*;
import java.util.Random;

public class KnightCreator implements UnitCreator {

    @Override
    public Unit createUnit() {
        return new Knight();
    }

    @Override
    public Unit createUnit(Team team) {
        //System.out.println("=============creating ninja.....");
        Knight newKnight = new Knight();
        newKnight.setFace(team.direction);
        newKnight.setTeam(team);
        newKnight.setRange(getKnightRange(team));
        return newKnight;
    }

    @Override
    public int getCost(){
        return 50;
    }

    private Rectangle getKnightRange(Team team) {
        int x = (int)team.tower.getRange().getLocation().getX();
        int y = (int)team.tower.getRange().getLocation().getY() + team.tower.getHeight() - Knight.KNIGHT_HEIGHT;
        int dither = team.random.nextInt(16) - 8;

        return new Rectangle(new Point(x, y+dither), new Dimension(Knight.KNIGHT_WIDTH, Knight.KNIGHT_HEIGHT));
    }
}
