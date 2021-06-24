import java.awt.*;

public class IdleState extends State{
    public IdleState(Unit u, String spriteType, double scale, StateHandler stateHandler) {
        super(u, "idle", ImageReader.read("assets/" + spriteType + "/idle", scale), stateHandler);
    }


    @Override
    public boolean remains(State nextState) {
        return nextState instanceof IdleState;
    }
}
