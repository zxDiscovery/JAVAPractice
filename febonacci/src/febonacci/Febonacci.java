package febonacci;

public class Febonacci {

    public int fib(int n) {

    	int[] num = new int[n+1];
    	
       	if (n > 0 ) {    	
       		num[0] = 0;
       		num[1] = 1;
    	
 
	    	for (int i = 2; i <= n; i++) {
	    		num[i] = num[i-1] + num[i-2];
	    	}
    	}
    	return num[n];
    	
    }
    
    public static void main(String[] args) {
    	Febonacci fb = new Febonacci();
    	System.out.println(fb.fib(1));
    }
}
