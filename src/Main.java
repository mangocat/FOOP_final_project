import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class Main{
    public static void main(){
        // create world
        World world = new World();
        // create unit creators
        Map<String, UnitCreator> creatorMap = new HashMap<>();
        String ninjaName = "ninja";
        creatorMap.put(ninjaName, new NinjaCreator());
        // create human, computer
        Human human = new Human(creatorMap);
        Computer computer = new Computer(creatorMap);
        // help world set human, computer
        world.setHuman(human);
        world.setComputer(computer);
        // create game
        Game game = new Game(world);
        // create buttons
        List<Button> buttons = new ArrayList<>();
        buttons.add(new LevelUpButton(game));
        buttons.add(new SummonButton(game, ninjaName));
    }
}