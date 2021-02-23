
public class main {
	private static boolean ifCanChange;

	public static void main(String[] args){
		int[] pay = new int[] {5,5,10,20};
		
		lemonade lemonade = new lemonade();
		
		ifCanChange = lemonade.ifCanChange(pay);
		
		if (ifCanChange == true) {
			System.out.printf("We can charge the money!");
		} else {
			System.out.printf("We can't charge the money!");
		}
	}
}
