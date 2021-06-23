public class IdleState extends State{
    public IdleState(Sprite s, String spriteType){
        super(s, "idle", ImageReader.read("assets/" + spriteType + "/idle"));
    }
}
