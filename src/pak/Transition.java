package pak;

public class Transition {
    private String fromState;
    private String toState;
    private Character symbol;

    public Transition(String fromState, String toState, Character symbol) {
        this.fromState = fromState;
        this.toState = toState;
        this.symbol = symbol;
    }

    public String getFromState() {
        return fromState;
    }

    public String getToState() {
        return toState;
    }

    public Character getSymbol() {
        return symbol;
    }

	@Override
	public String toString() {
		return "Transition [fromState=" + fromState + ", toState=" + toState + ", symbol=" + symbol + "]";
	}
   
}