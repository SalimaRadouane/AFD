package pak;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.*;

public class AFNDtoAFD {
    
    // Méthode principale pour transformer l'AFND en AFD
    public static Map<String, Map<Character, String>> transform(
    		Map<String, Map<Character, Set<String>>> afnd, 
    		String initialState, Set<String> finalStates) {
    // Étape 1 : Identifier les états atteignables
        Map<State, Map<Character, State>> transitionTable = new HashMap<>();
        Set<State> visited = new HashSet<>();
        Set<String> initialStates = new HashSet<>();
        initialStates.add(initialState);
        State initialStateSet = new State(initialStates, false);
        visited.add(initialStateSet);
        Queue<State> queue = new LinkedList<>();
        queue.add(initialStateSet);
        while (!queue.isEmpty()) {
            State currentState = queue.poll();
            Map<Character, State> transition = new HashMap<>();
            //afnd.get(currentState.states.iterator().next()) renvoie la Map des transitions du symbole d'entrée pour le premier état du currentState.
          //  keySet() retourne l'ensemble des symboles d'entrée pour cet état.
            for (char c : afnd.get(currentState.states.iterator().next()).keySet()) {
                Set<String> nextStates = new HashSet<>();
                for (String state : currentState.states) {
                	//afnd.get(state).getOrDefault(c, Collections.emptySet()) est utilisée pour obtenir
                	//les états de transition possibles à partir de state en utilisant le symbole d'entrée c.
            nextStates.addAll(afnd.get(state).getOrDefault(c, Collections.emptySet()));
                }
                //finalStates.stream().anyMatch(nextStates::contains) retourne true si 
                //au moins un état de finalStates est présent dans nextStates, sinon il 
                //retourne false.
       State nextState = new State(nextStates, finalStates.stream().anyMatch(nextStates::contains));
                if (!visited.contains(nextState)) {
                    visited.add(nextState);
                    queue.add(nextState);
                }
                transition.put(c, nextState);
            }
            transitionTable.put(currentState, transition);
        }
        
        // Étape 2 : Générer les états de l'AFD
        Map<String, Map<Character, String>> afd = new HashMap<>();
        for (State state : visited) {
            Map<Character, String> transition = new HashMap<>();
            for (char c : afnd.get(initialState).keySet()) {
                State nextState = transitionTable.get(state).getOrDefault(c, null);
                if (nextState != null) {
                    String nextStateStr = nextState.states.toString();
        afd.computeIfAbsent(state.states.toString(), k -> new HashMap<>()).put(c, nextStateStr);
                }
            }
        }
        return afd;
    }
    public static void main(String[] args) {
        // Définition de l'AFND
        Map<String, Map<Character, Set<String>>> afnd = new HashMap<>();
        afnd.put("q0", new HashMap<>());
        afnd.get("q0").put('a', Set.of("q0", "q1"));
        afnd.get("q0").put('b', Set.of("q0", "q2"));
        afnd.put("q1", new HashMap<>());
        afnd.get("q1").put('b', Set    .of("q2"));
        afnd.put("q2", new HashMap<>());
        afnd.get("q2").put('a', Set.of("q3"));
        afnd.get("q2").put('b', Set.of("q3"));
        afnd.put("q3", new HashMap<>());
        afnd.get("q3").put('a', Set.of("q3"));
        afnd.get("q3").put('b', Set.of("q3"));
        
        // Transformation en AFD
        Map<String, Map<Character, String>> afd = AFNDtoAFD.transform(afnd, "q0", Set.of("q3"));
        
        // Affichage de l'AFD
        System.out.println(afd);
    }}
