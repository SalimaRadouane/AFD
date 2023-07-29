package pak;




import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class main {




	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// Exemple d'automate fini déterministe pour le langage des mots qui commencent par "0" et finissent par "1"
       /* State[] Q=new State [3];
        Q[0]=new State("q0", false);
        Q[1]=new State("q1", false);
        Q[2]=new State("q2", true);*/
    
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
       
       
       
       Automate automate = new Automate();
       automate.addState("1");
       automate.addState("2");
       automate.addState("3");
       automate.addState("4");
       automate.addState("5");
       automate.addState("0");
       automate.addFinalState("2");
       automate.addFinalState("3");
       automate.addFinalState("5");
       automate.setInitialState("0");
       automate.addSymbol('1');
       automate.addSymbol('0');
       automate.addTransition("0", '0', "1");
       automate.addTransition("0", '1', "2");
       automate.addTransition("1", '0', "0");
       automate.addTransition("1", '1', "3");
       automate.addTransition("2", '0', "5");
       automate.addTransition("2", '1', "4");
       automate.addTransition("3", '0', "5");
       automate.addTransition("3", '1', "4");
       automate.addTransition("4", '0', "4");
       automate.addTransition("4", '1', "5");
       automate.addTransition("5", '0', "5");
       automate.addTransition("5", '1', "5");
       Reconnaissance T=new Reconnaissance(A);
        // Test si les mots "0101" et "001" sont acceptés par l'automate
        String mot1 = "01";
        String mot2 = "001010";
        System.out.println("Le mot \"" + mot1 + "\" est " + (T.accepte(mot1) ? "accepté" : "rejeté") + " par l'automate.");
        System.out.println("Le mot \"" + mot2 + "\" est " + (T.accepte(mot2) ? "accepté" : "rejeté") + " par l'automate.");
        T.generateWords(2);
        System.out.println(automate.minimize());
       
	}

}