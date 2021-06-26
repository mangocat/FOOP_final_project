import java.awt.*;

import javax.imageio.ImageIO;

import java.util.ArrayList;
import java.awt.Image;
import java.io.IOException;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.DirectoryStream;
import java.util.Collection;
import java.util.Collections;

public class Plane extends Unit {
    // static final int PLANE_HEIGHT = 200;
    // static final int PLANE_WIDTH = 185;
    static final int attackCd = 100; // attack every 30 updates
    static final int attackDistance = 300;
    static final int maxHp = 100;
    static final int speed = 3;
    static final int planeDamage = 50;

    public Plane(double scale) {
        super(speed, attackDistance, maxHp, planeDamage, attackCd);
        // this.height = 200;
        // Image sample;
        // try{
        //     Path samplePath = Paths.get("assets/plane/attack/0.png");
        //     sample = ImageIO.read(samplePath.toFile());
        // }catch(IOException e){
        //     throw new RuntimeException();
        // }
        // int originalHeight = sample.getHeight(null);
        // int originalWidth = sample.getWidth(null);
        // double scale = ((double)height)/originalHeight;
        // this.width = (int)(originalWidth*scale);

        //System.out.println("===================Plane width: " + width + ", height: " + height);



        StateHandler nullHandler = new StateHandler(null) {
            @Override
            public boolean isThis(State currentState) {
                throw new RuntimeException("undefined state");
            }
        };

        StateHandler idleHandler = new StateHandler(nullHandler) {
            @Override
            public State nextState(State currentState) {
                if (isThis(currentState)) return stateMap.get("idle");
                return super.nextState(currentState);
            }

            @Override
            public boolean isThis(State currentState) {
                return currentAttackCd > 0;
            }
        };

        StateHandler attackStateHandler = new StateHandler(idleHandler) {
            @Override
            public State nextState(State currentState) {
                if (isThis(currentState)) return stateMap.get("attack");

                return super.nextState(currentState);
            }

            @Override
            public boolean isThis(State currentState) {
                Collection<Sprite> attackableUnits = team.getWorld().getSprites(currentState.unit, face, attackDistance);
                return currentAttackCd <= 0 && !attackableUnits.isEmpty();
            }
        };

        StateHandler moveStateHandler = new StateHandler(attackStateHandler) {
            @Override
            public State nextState(State currentState) {
                if (isThis(currentState)) return stateMap.get("move");
                return super.nextState(currentState);
            }

            @Override
            public boolean isThis(State currentState) {
                boolean isMoving = (face == Direction.LEFT && (getEnemyBattleLine() + attackDistance) < front) || (face == Direction.RIGHT && (front + attackDistance) < getEnemyBattleLine());
                return isMoving;
            }
        };

        StateHandler deadHandler = new StateHandler(moveStateHandler) {
            @Override
            public State nextState(State currentState) {
                if (isThis(currentState)) return stateMap.get("dead");
                return super.nextState(currentState);
            }

            @Override
            public boolean isThis(State currentState) {
                return !isAlive();
            }
        };

        this.stateHandler = deadHandler;

        stateMap.put("attack", new AttackState(this, "plane", scale, stateHandler));
        stateMap.put("idle", new IdleState(this, "plane", scale, stateHandler));
        stateMap.put("move", new MoveState(this, "plane", scale, stateHandler));
        stateMap.put("dead", new DeadState(this, "plane", scale, stateHandler));
        currentState = stateMap.get("move");

    }
}
