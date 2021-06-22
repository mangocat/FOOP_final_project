import java.awt.*;
import java.lang.Math;

public class Tower extends Sprite{
    private int hp;
    private int attackDistance;
    private int damage;
    private Point location;

    public Tower(int hp, int attackDistance, Point location){
        this.hp = hp;
        this.attackDistance = attackDistance;
        this.location = location;
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics g) {

    }

    public void attack(Team opponentTeam) {
        for(Unit opponent : opponentTeam){
            if(Math.abs(opponent.getLocation().x - this.location.x) <= attackDistance){
                opponent.takeDamage(damage);
            }
        }
    }
}
