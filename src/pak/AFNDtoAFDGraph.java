package pak;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AFNDtoAFDGraph {
    // Méthode pour générer le code GraphViz représentant l'automate
    public static String generateGraphVizCode(Map<String, Map<Character, String>> afd, String initialState, Set<String> finalStates) {
        StringBuilder sb = new StringBuilder();
        sb.append("digraph Automaton {\n");
        sb.append("  rankdir=LR;\n");
        sb.append("  node [shape=circle];\n");

        // Ajouter les états
        for (String state : afd.keySet()) {
            if (state.equals(initialState)) {
                sb.append("  ").append(state).append(" [shape=doublecircle];\n");
            } else if (finalStates.contains(state)) {
                sb.append("  ").append(state).append(" [shape=doublecircle];\n");
            } else {
                sb.append("  ").append(state).append(";\n");
            }
        }

        // Ajouter les transitions
        for (Map.Entry<String, Map<Character, String>> entry : afd.entrySet()) {
            String state = entry.getKey();
            Map<Character, String> transitions = entry.getValue();
            for (Map.Entry<Character, String> transition : transitions.entrySet()) {
                char symbol = transition.getKey();
                String nextState = transition.getValue();
                sb.append("  ").append(state).append(" -> ").append(nextState).append(" [label=\"").append(symbol).append("\"];\n");
            }
        }

        sb.append("}\n");
        return sb.toString();
    }

    public static void main(String[] args) {
        // Définition de l'AFND
        Map<String, Map<Character, Set<String>>> afnd = new HashMap<>();
        // ...

        // Transformation en AFD
        Map<String, Map<Character, String>> afd = AFNDtoAFD.transform(afnd, "q0", Set.of("q3"));

        // Générer le code GraphViz
        String graphVizCode = generateGraphVizCode(afd, "q0", Set.of("q3"));

        // Écrire le code GraphViz dans un fichier
        try {
            PrintWriter writer = new PrintWriter("automaton.dot");
            writer.println(graphVizCode);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Appeler la commande GraphViz pour générer l'image
        try {
            String command = "dot -Tpng -o automaton.png automaton.dot";
            Process process = Runtime.getRuntime().exec(command);
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("L'automate a été généré dans le fichier automaton.png.");
    }
}
