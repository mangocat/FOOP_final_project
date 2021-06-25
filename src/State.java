import java.util.List;
import java.awt.*;

public abstract class State {
    Unit unit;
    final String name;
    final List<Image> images;
    final Image sample;
    int currentPosition;
    
    public State(Unit unit, String stateName, List<Image> stateImages){
        this.unit = unit;
        name = stateName;
        images = stateImages;
        sample = stateImages.get(0);
        currentPosition = 0;
    }
    public int getImageWidth(){
        return sample.getWidth(null);
    }
    public int getImageHeight(){
        return sample.getHeight(null);
    }
    public void reset(){
        currentPosition = -1;
    }
    public void doAction(){} // default: do nothing
    public abstract void update();
    public void render(Graphics g){
        Direction face = unit.getFace();
        Rectangle range = unit.getRange();
        Image currentImage = images.get(currentPosition);
        if(face == Direction.LEFT){
            //g.drawImage(images.get(currentPosition), range.x + range.width, range.y, -range.width, range.height, null);
            g.drawImage(currentImage, range.x + range.width, range.y, -currentImage.getWidth(null), currentImage.getHeight(null), null);
        }else{
            //g.drawImage(images.get(currentPosition), range.x, range.y, range.width, range.height, null);
            g.drawImage(currentImage, range.x, range.y, currentImage.getWidth(null), currentImage.getHeight(null), null);
        }
    }
    public boolean finished(){
        return currentPosition == images.size()-1;
    }
    public String toString(){
        return name;
    }
}
