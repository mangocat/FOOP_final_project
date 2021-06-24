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
            //if (unit.team instanceof Human) System.out.println("============attack next: " + next);
            if (!remains(next)) {
                unit.setState(next);
                next.update();
            }
        }
    }

    public abstract boolean remains(State nextState);

    public void render(Graphics g){
        Direction face = unit.getFace();
        Rectangle range = unit.getRange();
        try {
            if (face == Direction.LEFT) {
                g.drawImage(images.get(currentPosition), range.x + range.width, range.y, -range.width, range.height, null);
            } else {
                g.drawImage(images.get(currentPosition), range.x, range.y, range.width, range.height, null);
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public boolean finished(){
        return currentPosition == images.size()-1;
    }
    public String toString(){
        return name;
    }
}
