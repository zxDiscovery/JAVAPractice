
public class Solution {

	public int maxProfit(int[] prices) {
		int num = prices.length;
		int max = 0;
		int currentProfit = 0;
		
		for (int i = 0; i < num; i++) {
			for (int j = i; j< num; j++) {
				currentProfit = prices[j] - prices[i];
				
				if (currentProfit > max) {
					max = currentProfit;
				}
			}
		}
		
		return max;
	}
	
	public static void main(String[] args) {
		int[] numArray = new int[]{7,2,5,1,3};
		
		Solution2 s = new Solution2();
		
		System.out.println(s.maxProfit(numArray));
	}
}
