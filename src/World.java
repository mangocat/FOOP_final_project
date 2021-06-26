

import java.awt.*;
import java.util.Collection;
import static java.util.stream.Collectors.toList;

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

    public Collection<Sprite> getSprites(Sprite sprite, Direction face, int range){
        if(face==Direction.LEFT){ // find computer as targets
            return computer.getSprites().stream().filter(s -> (sprite.getFront() - s.getFront()) <= range).collect(toList());
        } else {
            return human.getSprites().stream().filter(s -> (s.getFront() - sprite.getFront()) <= range).collect(toList());
        }
    }

    public void reset(){
        human.reset();
        computer.reset();
        human.setEnemyBattleLine(computer.getEnemyBattleLine());
        computer.setEnemyBattleLine(human.getEnemyBattleLine());
    }
}
