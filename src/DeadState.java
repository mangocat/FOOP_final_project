public class DeadState extends State{
    public DeadState(Unit u, String unitType){
        super(u, "dead", ImageReader.read("assets/" + unitType + "/dead"));
    }
    public void update(){
        currentPosition++;
        if(currentPosition >= images.size()){
            // remove itself from the game
            unit.getTeam().removeSprite(unit);
        }
    }
}
