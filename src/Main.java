import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class Main{
    public static void main(String[] args){
        // create world
        World world = new World();

        // create unit creators
        Map<String, UnitCreator> creatorMap = new HashMap<>();
        String ninjaName = "ninja";
        String robotName = "robot";
        creatorMap.put(ninjaName, new NinjaCreator());
        creatorMap.put(robotName, new RobotCreator());

        // create human, computer
        Human human = new Human(creatorMap);
        Computer computer = new Computer(creatorMap);

        // help world set human, computer
        world.setHuman(human);
        world.setComputer(computer);

        // create game
        Game game = new Game(world);

        // create summon buttons
        List<Button> buttons = new ArrayList<>();
        buttons.add(new SummonButton(game, robotName));
        buttons.add(new SummonButton(game, ninjaName));

        // the last button should be level up button
        buttons.add(new LevelUpButton(game));

        // give the buttons to human
        human.setButtons(buttons);

        // create game view
        GameView view = new GameView(game, buttons);

        // prepare the lauch the game
        view.launch();

        // start the game
        game.start();
    }
}