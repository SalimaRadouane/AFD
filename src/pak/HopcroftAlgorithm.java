package pak;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class HopcroftAlgorithm {

	 public static Automate minimize(Automate automaton) {
	        // Initialisation des partitions
	        Set<Set<String>> finalPartitions = new HashSet<>();
	        Set<Set<String>> nonFinalPartitions = new HashSet<>();
	        Set<String> finalStates = automaton.getFinalStates();
	        Set<String> nonFinalStates = automaton.getStates();
	        nonFinalStates.removeAll(finalStates);
	        finalPartitions.add(finalStates);
	        nonFinalPartitions.add(nonFinalStates);
	        Set<Set<String>> allPartitions = new HashSet<>();
	        allPartitions.addAll(finalPartitions);
	        allPartitions.addAll(nonFinalPartitions);

	        // Boucle principale
	        boolean hasChanged = true;
	        while (hasChanged) {
	            hasChanged = false;
	            for (char c : automaton.getAlphabet()) {
	                for (Set<String> partition : allPartitions) {
	                    // Séparation de la partition en deux sous-partitions
	                    Set<String> reachableStates = automaton.getReachableStates(partition, c);
	                    if (reachableStates.isEmpty()) {
	                        continue;
	                    }
	                    Set<Set<String>> newPartitions = new HashSet<>();
	                    for (Set<String> p : allPartitions) {
	                        if (!reachableStates.containsAll(p)) {
	                            newPartitions.add(p);
	                        } else {
	                            Set<String> difference = new HashSet<>(p);
	                            difference.removeAll(reachableStates);
	                            if (!difference.isEmpty()) {
	                                newPartitions.add(difference);
	                                newPartitions.add(reachableStates);
	                                hasChanged = true;
	                            } else {
	                                newPartitions.add(p);
	                            }
	                        }
	                    }
	                    allPartitions = newPartitions;
	                }
	            }
	        }

	        // Construction de l'automate minimal
	        Map<Set<String>, String> stateMap = new HashMap<>();
	        Automate minimalAutomaton = new Automate();
	        for (Set<String> partition : allPartitions) {
	        	String newState = new String();
	            for (String s : partition) {
	                stateMap.put(partition, newState);
	                if (finalStates.contains(s)) {
	                    minimalAutomaton.addFinalState(newState);
	                }
	                if (s.equals(automaton.getInitialState())) {
	                    minimalAutomaton.setInitialState(newState);
	                }
	            }
	        }
	        for (Set<String> partition : allPartitions) {
	        	String fromState = stateMap.get(partition);
	            for (char c : automaton.getAlphabet()) {
	                Set<String> reachableStates = automaton.getReachableStates(partition, c);
	                String toState = stateMap.get(reachableStates);
	                minimalAutomaton.addTransition(fromState, c, toState);
	            }
	        }
	        return minimalAutomaton;
	    }
	 public static void main(String[] args) {
		    // Création de l'automate
		    Automate automate = new Automate();
		    automate.addSymbol('a');
		    automate.addSymbol('b');
		    automate.addSymbol('c');
		    automate.setInitialState("q0");
		    automate.addFinalState("q2");
		    automate.addState("q0");
		    automate.addState("q1");
		    automate.addState("q2");
		    automate.addTransition("q0", 'a', "q1");
		    automate.addTransition("q1", 'b', "q2");
		    automate.addTransition("q0", 'c', "q2");
		    
		    // Affichage de l'automate original
		    System.out.println("Automate original :");
		    System.out.println(automate);

		    // Minimisation de l'automate
		    Automate minimalAutomate = HopcroftAlgorithm.minimize(automate);

		    // Affichage de l'automate minimal
		    System.out.println("Automate minimal :");
		   System.out.println(minimalAutomate.toString());
		}

}