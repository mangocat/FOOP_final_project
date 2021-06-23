public class DeadState extends State{
    public DeadState(Unit u, String unitType, int preferredWidth){
        super(u, "dead", ImageReader.read("assets/" + unitType + "/dead", preferredWidth));
    }
    public void update(){
        currentPosition++;
        if(currentPosition >= images.size()){
            // remove itself from the game
            unit.getTeam().removeSprite(unit);
        }
    }
}
