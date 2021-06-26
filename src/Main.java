import java.util.Map;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;

public class Main{
    public static void main(String[] args){
        // create world
        World world = new World();

        // create unit creators
        Map<String, UnitCreator> creatorMap = new LinkedHashMap<>();
        String ninjaName = "ninja";
        String robotName = "robot";
        String knightName = "knight";
        String planeName = "plane";
        creatorMap.put(ninjaName, new NinjaCreator());
        creatorMap.put(knightName, new KnightCreator());
        creatorMap.put(robotName, new RobotCreator());
        creatorMap.put(planeName, new PlaneCreator());

        // create human, computer
        Human human = new Human(creatorMap);
        Computer computer = new Computer(creatorMap);

        // help world set human, computer
        world.setHuman(human);
        world.setComputer(computer);

        // create gamegit 
        Game game = new Game(world);

        // create summon buttons
        List<Button> buttons = new ArrayList<>();
        buttons.add(new SummonButton(game, ninjaName));
        buttons.add(new SummonButton(game, knightName));
        buttons.add(new SummonButton(game, robotName));
        buttons.add(new SummonButton(game, planeName));

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