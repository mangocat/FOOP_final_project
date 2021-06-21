public class MoveState extends State{
    public MoveState(Sprite s, String spriteType){
        super(s, "move", ImageReader.read("assets/" + spriteType + "/move"));
    }
}
