public class DeadState extends State{
    public DeadState(Sprite s, String spriteType){
        super(s, "dead", ImageReader.read("assets/" + spriteType + "/dead"));
    }
}
