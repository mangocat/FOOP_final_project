import java.awt.*;

public class World {
    // need to check if the world needs to construct the team
    final Team human, computer;
    public World(Team human, Team computer){
        this.human = human;
        this.computer = computer;
    }
    public void update(){
        human.update();
        computer.update();
    }
    public void render(Graphics g){
        human.render(g);
        computer.render(g);
    }
    public Team getHuman(){return human;}
    public Team getComputer(){return computer;}
}
