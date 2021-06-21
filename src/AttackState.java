public class AttackState extends State{
    public AttackState(Sprite s, String spriteType){
        super(s, "attack", ImageReader.read("assets/" + spriteType + "/attack"));
    }
}
