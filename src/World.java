

import java.awt.*;
import java.util.Collection;
import static java.util.stream.Collectors.toSet;

public class World {
    // need to check if the world needs to construct the team
    Human human;
    Computer computer;
    public void update(){
        human.update(computer.getBattleLine());
        computer.update(human.getBattleLine());
    }
    public void render(Graphics g){
        human.render(g);
        computer.render(g);
    }
    public void setHuman(Human h){h.setWorld(this);human = h;}
    public Human getHuman(){return human;}
    public void setComputer(Computer c){c.setWorld(this);computer = c;}
    public Computer getComputer(){return computer;}

    public Collection<Sprite> getSprites(Direction face, Point location, int range){
        if(face==Direction.LEFT){ // find computer as targets
            return computer.getSprites().stream().filter(s -> (location.x - s.getLocation().x) < range).collect(toSet());
        } else {
            return human.getSprites().stream().filter(s -> (s.getLocation().x - location.x) < range).collect(toSet());
        }
    }
}
