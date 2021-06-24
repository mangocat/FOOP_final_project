public class DeadState extends State{
    public DeadState(Unit u, String unitType, double scale, StateHandler stateHandler){
        super(u, "dead", ImageReader.read("assets/" + unitType + "/dead", scale), stateHandler);
    }

    @Override
    public void update(){
        currentPosition++;
        if(currentPosition >= images.size()){
            // remove itself from the game
            unit.getTeam().removeSprite(unit);
        }
    }

    @Override
    public boolean remains(State nextState) {
        return nextState instanceof DeadState;
    }
}
