package pak;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Stack;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Automate {
    private Set<String> states;//Un ensemble de chaînes de caractères représentant les états de l'automate.
    private Set<Character> alphabet; // Un ensemble de caractères représentant l'alphabet de l'automate
    private String initialState; //Une chaîne de caractères représentant l'état initial de l'automate.
    private Set<String> finalStates; //  Un ensemble de chaînes de caractères représentant les états finaux de l'automate.
    private boolean isNondeterministic;
    private Map<Pair<String, Character>, String> transitions = new HashMap<>();

    public Automate(boolean isNondeterministic) {
        this.states = new HashSet<>();
        this.alphabet = new HashSet<>();
        this.finalStates = new HashSet<>();
        this.transitions = new HashMap<>();

        this.isNondeterministic = isNondeterministic;
    }
    public Automate() {
        this.states = new HashSet<>();
        this.alphabet = new HashSet<>();
        this.finalStates = new HashSet<>();
        this.transitions = new HashMap<>();

      
    }
//Ajoute un état à l'ensemble des états de l'automate.
    public void addState(String state) {
        states.add(state);
    }
//Définit l'état initial de l'automate.
    public void setInitialState(String state) {
        this.initialState = state;
    }
// Ajoute un état final à l'ensemble des états finaux de l'automate.
    public void addFinalState(String state) {
        finalStates.add(state);
    }
   // Ajoute un symbole à l'alphabet de l'automate.
    public void addSymbol(Character symbol) {
        alphabet.add(symbol);
    }
 //   Ajoute une transition entre deux états de l'automate pour un symbole donné.
    public void addTransition(String fromState, Character symbol, String toState) {
        // Vérifier si l'état source existe
       if (!states.contains(fromState)) {
           // throw new IllegalArgumentException("L'état source n'existe pas");
        }

        // Vérifier si l'état de destination existe
        if (!states.contains(toState)) {
        //  throw new IllegalArgumentException("L'état de destination n'existe pas");
       }

        // Vérifier si le symbole est valide
        if (!alphabet.contains(symbol)) {
         //  throw new IllegalArgumentException("Le symbole n'est pas dans l'alphabet de l'automate");
        }

        // Ajouter la transition
         transitions.put(new Pair(fromState, symbol), toState);
    }

	@Override
	public String toString() {
		return "Automate [states=" + states + ", alphabet=" + alphabet + ", initialState=" + initialState
				+ ", finalStates=" + finalStates + ", isNondeterministic=" + isNondeterministic + ", transitions="
				+ transitions.toString() + "]";
	}
	public Set<String> getStates() {
		return states;
	}

	public void setStates(Set<String> states) {
		this.states = states;
	}

	public Set<Character> getAlphabet() {
		return alphabet;
	}

	public void setAlphabet(Set<Character> alphabet) {
		this.alphabet = alphabet;
	}

	public Set<String> getFinalStates() {
		return finalStates;
	}

	public void setFinalStates(Set<String> finalStates) {
		this.finalStates = finalStates;
	}

	public boolean isNondeterministic() {
		return isNondeterministic;
	}

	public void setNondeterministic(boolean isNondeterministic) {
		this.isNondeterministic = isNondeterministic;
	}

	public String getInitialState() {
		return initialState;
	}
	public boolean isInitialState(String state) {
	    return initialState.equals(state);
	}
	public boolean isFinalState(String state) {
	    return finalStates.contains(state);
	}

	public Map<Pair<String, Character>, String> getTransitions() {
		return transitions;
	}
	public int getStateSize() {
	    return states.size();
	}

	public void addNonFinalState(String state) {
	    states.add(state);
	    finalStates.remove(state);
	}


	public void setTransitions(Map<Pair<String, Character>, String> transitions) {
		this.transitions = transitions;
	}
	public String getNextState(String currentState, char inputSymbol) {
	    return transitions.get(new Pair<>(currentState, inputSymbol));
	}
    public boolean isExist(String fromeState,Character symbole) {
    	Pair<String,Character> P=new Pair<>(fromeState, symbole);
    	if(!transitions.containsKey(P)){
    		return false;
    	}
    
			return true;
	
		
    	
    }
    public String getnextstate(String fromeState,Character symbole) {
    	Pair<String,Character> P=new Pair<>(fromeState, symbole);
    	
    		return transitions.get(P);  // q0--(a)--->q1
    	
		
		
	}
    public Set<String> getReachableStates(Set<String> partition, char c) {
        Set<String> reachableStates = new HashSet<>();
        for (String state : partition) {
            String nextState = transitions.get(new Pair<>(state, c));
            if (nextState != null) {
                reachableStates.add(nextState);
            }
        }
        return reachableStates;
    }
    public Automate minimize() {
        Automate minimizedAutomate = new Automate();

        // Copie des éléments de l'automate original
        minimizedAutomate.setAlphabet(new HashSet<>(this.alphabet));
        minimizedAutomate.setInitialState(this.initialState);
        minimizedAutomate.setFinalStates(new HashSet<>(this.finalStates));
        minimizedAutomate.setNondeterministic(this.isNondeterministic);

        // Création des classes d'équivalence initiales
        Set<Set<String>> equivalenceClasses = new HashSet<>();
        Set<String> nonFinalStates = new HashSet<>(this.states);
        nonFinalStates.removeAll(this.finalStates);
        equivalenceClasses.add(this.finalStates);
        equivalenceClasses.add(nonFinalStates);

        // Itération jusqu'à ce qu'il n'y ait plus de classes à diviser
        while (true) {
            Set<Set<String>> newEquivalenceClasses = new HashSet<>();
            for (Set<String> equivalenceClass : equivalenceClasses) {
                for (Character symbol : this.alphabet) {
                    Set<String> reachableStates = new HashSet<>();
                    for (String state : equivalenceClass) {
                        reachableStates.add(this.transitions.get(new Pair<>(state, symbol)));
                    }
                    
                    // Trouve la classe d'équivalence contenant les états atteignables
                    Set<String> containingClass = null;
                    for (Set<String> eqClass : newEquivalenceClasses) {
                        if (eqClass.containsAll(reachableStates) && reachableStates.containsAll(eqClass)) {
                            containingClass = eqClass;
                            break;
                        }
                    }
                    if (containingClass == null) {
                        containingClass = new HashSet<>(reachableStates);
                        newEquivalenceClasses.add(containingClass);
                    }
                    // Ajoute la transition entre les classes
                    minimizedAutomate.getTransitions().put(new Pair<>(equivalenceClass.iterator().next(), symbol), 
                    		containingClass.iterator().next());
                }
            }
            if (newEquivalenceClasses.equals(equivalenceClasses)) {
                break;
            } else {
                equivalenceClasses = newEquivalenceClasses;
            }
        }

        // Création des nouveaux états et transitions de l'automate minimisé
        Set<String> minimizedStates = new HashSet<>();
        Map<String, String> stateMapping = new HashMap<>();
        for (Set<String> equivalenceClass : equivalenceClasses) {
            String newState="" ;
            minimizedStates.add(newState);
            for (String state : equivalenceClass) {
                stateMapping.put(state, newState);
                if (this.finalStates.contains(state)) {
                    minimizedAutomate.getFinalStates().add(newState);
                }
            }
            if (equivalenceClass.contains(this.initialState)) {
                minimizedAutomate.setInitialState(newState);
            }
        }
        minimizedAutomate.setStates(minimizedStates);
        for (Entry<Pair<String, Character>, String> transition : minimizedAutomate.getTransitions().entrySet()) {
            transition.setValue(stateMapping.get(transition.getValue()));
        }

        return minimizedAutomate;
    }
}