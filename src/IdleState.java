public class IdleState extends State{
    public IdleState(Unit u, String spriteType){
        super(u, "idle", ImageReader.read("assets/" + spriteType + "/idle"));
    }
}
