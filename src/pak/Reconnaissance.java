package pak;
import java.util.Map;
import java.util.Set;

public class Reconnaissance {

	    
	      
	    private Automate a;
	    
	    public Reconnaissance(Automate A) 
	                   {
	        this.a = A;
	        
	    }
	    
	  

		public boolean accepte(String mot) { //aabb //1101
	    	String q = a.getInitialState();  // état courant
	        
	        for (int i = 0; i < mot.length(); i++) {
	        	// symbole courant
	            Character c = Character.valueOf(mot.charAt(i));  
	            
	            if (!a.isExist(q, c)) {
	            	// pas de transition pour ce symbole
	                return false;  
	            }
	            // mettre à jour l'état courant
	            q = a.getnextstate(q, c);  
	        }
	        // vrai si l'état courant est un état final

	        return a.getFinalStates().contains(q);  	    }
		
		public void generateWords(int n) {
		    generateWords("", 0, n);
		}

		private void generateWords(String currentWord,
				int currentLength, int maxLength) {
		    // Vérifie si le mot courant est accepté par l'automate
		    if (accepte(currentWord)) {
		        System.out.println(currentWord);
		    }
		    
		    // Vérifie si la longueur du mot courant 
		    //est inférieure à la longueur maximale
		    if (currentLength < maxLength) {
		        // Génère tous les mots possibles en ajoutant un caractère
		        for (Character c : a.getAlphabet()) {
		            generateWords(currentWord + c, 
		            		currentLength + 1, maxLength);
		        }
		    }
		}
	}