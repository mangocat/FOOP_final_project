import java.util.List;
import java.awt.*;

public abstract class State {
    Unit unit;
    final String name;
    final List<Image> images;
    final Image sample;
    int currentPosition;
    protected StateHandler stateHandler;
    
    public State(Unit unit, String stateName, List<Image> stateImages, StateHandler stateHandler){
        this.unit = unit;
        name = stateName;
        images = stateImages;
        sample = stateImages.get(0);
        currentPosition = 0;
        this.stateHandler = stateHandler;
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
    //public abstract void update();
    public void update() {
        currentPosition++;
        if (currentPosition >= images.size()) {
            reset();
            currentPosition++;
            State next = stateHandler.nextState(this);
            if (!remains(next)) {
                unit.setState(next);
                next.update();
            }
        }
    }

    public abstract boolean remains(State nextState);

    public void render(Graphics g){
        if(currentPosition<0 || currentPosition>=images.size()){
            currentPosition = 0;
        }
        Direction face = unit.getFace();
        Rectangle range = unit.getRange();
        Image currentImage = images.get(currentPosition);
        if(face == Direction.LEFT){
            g.drawImage(currentImage, range.x + range.width, range.y, -currentImage.getWidth(null), currentImage.getHeight(null), null);
        }else{
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
