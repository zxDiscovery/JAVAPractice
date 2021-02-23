
public class lemonade {
	
	int five = 0;
	int ten = 0;
	
	public boolean ifCanChange(int[] bills) {
		int totallong = 0;
        int arrylong = bills.length;
		
		for ( int bill : bills ) {
			totallong++;
			if (bill == 5) {
				five++;
				
				System.out.printf("five have %d\n", five);
				System.out.printf("ten have %d\n", ten);
				continue;
			} else if (bill == 10) {
				if (five >= 1) {
					five--;
					ten++;
					System.out.printf("five have %d\n", five);
					System.out.printf("ten have %d\n", ten);
					continue;
				} else {
					return false;
				}
			} else if (bill == 20) {
				if (ten >= 1 && five >= 1) {
					ten--;
					five--;
					System.out.printf("five have %d\n", five);
					System.out.printf("ten have %d\n", ten);
					continue;
				} else if (five >= 3) {
					five = five - 3;
					System.out.printf("five have %d\n", five);
					System.out.printf("ten have %d\n", ten);
					continue;
				} else {
					return false;
				}
			} 
		} 
		
		if (totallong == arrylong) {
			System.out.printf("totallong is %d\n", totallong);
			System.out.printf("arrylong is %d\n", arrylong);
			return true;
		} else {
			System.out.printf("totallong is %d\n", totallong);
			System.out.printf("arrylong is %d\n", arrylong);
			return false;			
		}

	}
}
