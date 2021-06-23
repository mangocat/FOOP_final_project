public class NinjaCreator implements UnitCreator {

    @Override
    public Unit createUnit() {
        return new Ninja();
    }
    @Override
    public int getCost(){
        return 50;
    }
}
