public abstract class StateHandler {
    protected StateHandler successor;

    public StateHandler(StateHandler successor) {
        this.successor = successor;
    }

    public abstract boolean isThis(State currentState);

    public State nextState(State currentState) {
        if (successor != null) {
            return successor.nextState(currentState);
        }
        else return null;
    }
}
