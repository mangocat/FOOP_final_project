package controller;

import model.Human;
import model.World;

public class Game extends GameLoop {
    private final Human humanUnit;
    private final World world;

    public Game(World world) {
        this.world = world;
        this.humanUnit = world.getHuman();
    }

    public void humanLevelUp() {
        this.humanUnit.levelUp();
    }

    public void humanSummon(String spriteName) {
        this.humanUnit.createSprite(spriteName);
    }

    @Override
    public World getWorld() {
        return this.world;
    }
}
