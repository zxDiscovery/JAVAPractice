import java.util.Stack;

public class main {
    public static String predictPartyVictory(String senate) {
    	
    	char camp = 0;
    	    	
        int i = senate.length();
        
        MemberParliamentary MP[] = new MemberParliamentary[i];
		
    	for (int j = 0; j < i; j++) {
    		
    		MP[j] = new MemberParliamentary();
    		
    		MP[j].setRight(true);
    		MP[j].setCamp(senate.charAt(j));  		
    	}
    	
    	for (int j = 0; j < i; j++) {
    		usingRight(i, j, MP);
    	}    	
    	
    	for (int j = 0; j < i; j++) {
    		if (MP[j].IfHasRight == true) {
    			camp = MP[j].camp;
    			break;
    		}
    	}
    	
    	if (camp == 'R') {
    		return "Radiant";
    	} else if (camp == 'D') {
    	    return "Dire";
    	} else {
    		return "error";
    	}
    }
    
    public static void usingRight(int total, int current, MemberParliamentary[] MP) {
    	
    	if (MP[current].IfHasRight == true) {
   			int j = current;
    		
    		while (j > 0) {
    			j--;
    			if (MP[current].camp != MP[j].camp && MP[j].IfHasRight == true) {
    				MP[j].setRight(false);
    				return;
    			} 
    			
    		}
    		
    		j = current;
    		
    		while (j < total-1 ) {
    			j++;
    			if (MP[current].camp != MP[j].camp && MP[j].IfHasRight == true) {
    				MP[j].setRight(false);
    				return;
    			} 
    		}
    	}
    	
    	return;
    }
    
    public static void main(String[] args) {
        String senate = new String("RDDRDDDDRRRDRRRD");
    	
    	String camp = predictPartyVictory(senate);
    	
    	System.out.printf("The camp is %s\n", camp);
    }


}
