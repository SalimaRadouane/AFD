package pak;

import java.util.Set;



 public class State {
    Set<String> states; // ensemble des états de l'AFND correspondants
    boolean isFinal; // vrai si l'état est final dans l'AFD
    
    public State(Set<String> states, boolean isFinal) {
        this.states = states;
        this.isFinal = isFinal;
    }
    
    // Redéfinition de equals et hashCode pour permettre l'utilisation de Set<State>
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof State) {
            State other = (State) obj;
            return states.equals(other.states);
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return states.hashCode();
    }
}