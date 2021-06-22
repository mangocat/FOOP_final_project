public class DeadState extends State{
    public DeadState(Sprite s, String spriteType){
        super(s, "dead", ImageReader.read("assets/" + spriteType + "/dead"));
    }
    @Override
    public void update(){
        currentPosition++;
        if(currentPosition >= images.size()){
            // remove itself from the game
            sprite.getTeam().removeSprite(sprite);
        }
    }
}
