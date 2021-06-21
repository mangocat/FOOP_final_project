import java.util.List;
import java.awt.*;

public class State {
    Sprite sprite;
    final String name;
    final List<Image> images;
    int currentPosition;
    
    public State(Sprite sprite, String stateName, List<Image> stateImages){
        this.sprite = sprite;
        name = stateName;
        images = stateImages;
        currentPosition = -1;
    }
    public void update(){
        currentPosition++;
        if(currentPosition >= images.size()){
            currentPosition = 0;
        }
    }
    public void render(Graphics g){
        Direction face = sprite.getFace();
        Rectangle range = sprite.getRange();
        if(face == Direction.LEFT){
            g.drawImage(images.get(currentPosition), range.x + range.width, range.y, -range.width, range.height, null);
        }else{
            g.drawImage(images.get(currentPosition), range.x, range.y, range.width, range.height, null);
        }
    }
    public boolean finished(){
        return currentPosition == images.size()-1;
    }
    public String toString(){
        return name;
    }
}
