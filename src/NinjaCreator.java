import java.awt.*;
import java.util.Random;

public class NinjaCreator implements UnitCreator {

    @Override
    public Unit createUnit() {
        return new Ninja();
    }

    @Override
    public Unit createUnit(Team team) {
        //System.out.println("=============creating ninja.....");
        Ninja newNinja = new Ninja();
        newNinja.setFace(team.direction);
        newNinja.setTeam(team);
        newNinja.setRange(getNinjaRange(team));
        return newNinja;
    }

    @Override
    public int getCost(){
        return 50;
    }

    private Rectangle getNinjaRange(Team team) {
        int x = (int)team.tower.getRange().getLocation().getX();
        int y = (int)team.tower.getRange().getLocation().getY() + team.tower.getHeight() - Ninja.NINJA_HEIGHT;
        int dither = team.random.nextInt(16) - 8;

        return new Rectangle(new Point(x, y+dither), new Dimension(Ninja.NINJA_WIDTH, Ninja.NINJA_HEIGHT));
    }
}
