

// import team.Human; /*may modify here*/
// import model.World;

public class Game extends GameLoop {
	private final Human humanUnit;
	private final World world;

	public Game(World world, Human humanUnit){
		this.humanUnit = humanUnit;
		this.world = world;
	}

	public void humanLevelUp(){
		this.humanUnit.levelUp();
	}

	public void humanSummon(String spriteName){
		this.humanUnit.createSprite(spriteName);
	}

	@Override
	protected World getWorld() {
		return this.world;
	}
}
