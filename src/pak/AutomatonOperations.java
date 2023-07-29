package pak;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class AutomatonOperations {

	 public static Automate union(Automate A, Automate B) {
	        Automate C = new Automate();
	        
	        //Alphabet de autromate union C
	        for(Character i : A.getAlphabet()) {
	        	C.addSymbol(i);
	        }
	        // création des nouveaux états de C, qui sont la concaténation des états de A et B
	        for (String stateA : A.getStates()) {
	            for (String stateB : B.getStates()) {
	                String stateC = stateA + stateB;
	                C.addState(stateC);
	                
	                // déterminer si l'état est final ou non
	                if (A.getFinalStates().contains(stateA) || B.getFinalStates().contains(stateB)) {
	                    C.addFinalState(stateC);
	                }
	                
	                // déterminer l'état initial de C
	                if (stateA.equals(A.getInitialState()) && stateB.equals(B.getInitialState())) {
	                    C.setInitialState(stateC);
	                }
	            }
	        }
	        
	        // création des nouvelles transitions de C
	        for (String stateC : C.getStates()) {
	            String stateA = stateC.substring(0, A.getStateSize());
	            String stateB = stateC.substring(A.getStateSize());
	            for (char symbol : A.getAlphabet()) {
	                String nextStateA = A.getNextState(stateA, symbol);
	                String nextStateB = B.getNextState(stateB, symbol);
	                if (nextStateA != null && nextStateB != null) {
	                    String nextStateC = nextStateA + nextStateB;
	                    C.addTransition(stateC, symbol, nextStateC);
	                }
	            }
	        }
	        
	        return C;
	    }
	 public static Automate intersection(Automate A, Automate B) {
		    Automate C = new Automate();
		    
		  //Alphabet de autromate union C
	        for(Character i : A.getAlphabet()) {
	        	C.addSymbol(i);
	        }
		    // création des nouveaux états de C, qui sont la concaténation des états de A et B
		    for (String stateA : A.getStates()) {
		        for (String stateB : B.getStates()) {
		            String stateC = stateA + stateB;
		            C.addState(stateC);
		            
		            // déterminer si l'état est final ou non
		            if (A.getFinalStates().contains(stateA) && B.getFinalStates().contains(stateB)) {
		                C.addFinalState(stateC);
		            }
		            
		            // déterminer l'état initial de C
		            if (stateA.equals(A.getInitialState()) && stateB.equals(B.getInitialState())) {
		                C.setInitialState(stateC);
		            }
		        }
		    }
		    
		    // création des nouvelles transitions de C
		    for (String stateC : C.getStates()) {
		        String stateA = stateC.substring(0, A.getStateSize());
		        String stateB = stateC.substring(A.getStateSize());
		        for (char symbol : A.getAlphabet()) {
		            String nextStateA = A.getNextState(stateA, symbol);
		            String nextStateB = B.getNextState(stateB, symbol);
		            if (nextStateA != null && nextStateB != null) {
		                String nextStateC = nextStateA + nextStateB;
		                C.addTransition(stateC, symbol, nextStateC);
		            }
		        }
		    }
		    
		    return C;
		}
	 public static Automate complement(Automate A) {
		    Automate C = new Automate();
		  //Alphabet de autromate union C
	        for(Character i : A.getAlphabet()) {
	        	C.addSymbol(i);
	        }
		    // Copie de l'alphabet et des états
		    C.setAlphabet(A.getAlphabet());
		    for (String state : A.getStates()) {
		        C.addState(state);
		        if (A.getFinalStates().contains(state)) {
		            // Si l'état est final, on l'ajoute comme non-final dans C
		            C.addNonFinalState(state);
		        } else {
		            // Si l'état est non-final, on l'ajoute comme final dans C
		            C.addFinalState(state);
		        }
		        if (state.equals(A.getInitialState())) {
		            C.setInitialState(state);
		        }
		    }
		    // Copie des transitions
		    for (String state : A.getStates()) {
		        for (char symbol : A.getAlphabet()) {
		            String nextState = A.getNextState(state, symbol);
		            C.addTransition(state, symbol, nextState);
		        }
		    }
		    return C;
		}
	 public static Automate mirror(Automate A) {
		    Automate mirroredAutomaton = new Automate();
		    
		  //Alphabet de autromate union C
	        for(Character i : A.getAlphabet()) {
	        	mirroredAutomaton.addSymbol(i);
	        }
		    // Ajouter tous les états
		    for (String state : A.getStates()) {
		        mirroredAutomaton.addState(state);
		    }
		    // Changer l'état initial en final et vice versa
		    for (String state : A.getStates()) {
		        if (A.isInitialState(state)) {
		            mirroredAutomaton.addFinalState(state);
		        } else if (A.isFinalState(state)) {
		            mirroredAutomaton.setInitialState(state);
		        } else {
		            mirroredAutomaton.addNonFinalState(state);
		        }
		    }  
		    // Inverser les transitions
		    for (String state : A.getStates()) {
		        for (char symbol : A.getAlphabet()) {
		            String nextState = A.getNextState(state, symbol);
		            if (nextState != null) {
		                mirroredAutomaton.addTransition(nextState, symbol, state);
		            }
		        }
		    }
		    
		    return mirroredAutomaton;
		}
	 
public class Main {
	    public static void main(String[] args) {
	        // Automate 1
	    	Automate A=new  Automate();
	        
	        A.addTransition("q0",'0', "q1");
	        A.addTransition("q0",'1', "q0");
	        A.addTransition("q1",'1', "q2");
	        A.addTransition("q1",'0', "q1");
	        A.addTransition("q2",'1', "q2");
	        A.addTransition("q2",'0', "q1");
	         A.addState( "q0");
	         A.addState("q1");
	         A.addState("q2");
	         A.setInitialState("q0");
	         A.addFinalState("q2");
	        
	        A.addSymbol('1');
	        A.addSymbol('0');
	        // Automate 2
	        Automate B=new  Automate();
	        
	        B.addTransition("q0",'0', "q1");
	        B.addTransition("q0",'1', "q0");
	        B.addTransition("q1",'1', "q2");
	        B.addTransition("q1",'0', "q1");
	        B.addTransition("q2",'1', "q2");
	        B.addTransition("q2",'0', "q1");
	         B.addState( "q0");
	         B.addState("q1");
	         B.addState("q2");
	         B.setInitialState("q0");
	         B.addFinalState("q2");
	        
	        B.addSymbol('1');
	        B.addSymbol('0');
	        // Union des deux automates
	        Automate unionAFD = AutomatonOperations.union(A, B);
	        Automate compl =AutomatonOperations.complement(A);

	        Automate mirr =AutomatonOperations.mirror(A);
	        Automate inters =AutomatonOperations.intersection(A, B);
	        // Test de l'union avec des mots
	        String word1 = "100";
	        String word2 = "0101";
	        String word3 = "0011";
	        Reconnaissance T=new Reconnaissance(A);
	        Reconnaissance F=new Reconnaissance(B);
	        Reconnaissance union=new Reconnaissance(unionAFD);
	        Reconnaissance comp =new Reconnaissance(compl);
	        Reconnaissance mir =new Reconnaissance(mirr);
	        Reconnaissance inte = new Reconnaissance(inters);
	        System.out.println("AFD1 accepte " + word1 + " : " + T.accepte(word1));
	        System.out.println("AFD2 accepte " + word2 + " : " + F.accepte(word2));
	        System.out.println("Union accepte " + word1 + " : " + union.accepte(word1));
	        System.out.println("Union accepte " + word2 + " : " + union.accepte(word2));
	        System.out.println("Union accepte " + word3 + " : " + union.accepte(word3));
	        System.out.println(comp.accepte(word1));
	        System.out.println(inte.accepte(word1));
	        System.out.println("Automate :");
	        System.out.println(inters);
	    }
	}

}