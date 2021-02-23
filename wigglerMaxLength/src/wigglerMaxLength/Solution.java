package wigglerMaxLength;

import java.util.Stack;

public class Solution {
    public int wiggleMaxLength(int[] nums) {
    	
    	int numlen = nums.length;
    	    	
    	Stack<Integer> intSt = new Stack<Integer>(); 
    	
    	for (int current = 0;  current < numlen ; current++) {
    		if (0 == current) {
    			intSt.push(nums[current]);
    		}
    		
    		if (intSt.size() == 1) {
    			if (nums[current] != intSt.get(0)) {
    				intSt.push(nums[current]);
    			}
    		} else if (intSt.size() > 1) {
    			int stacksize = intSt.size();
    			int sum = intSt.get(stacksize-1) -  intSt.get(stacksize-2);
    			
    			if (sum > 0) {
    				if (nums[current] - intSt.get(stacksize-1) < 0) {
    					intSt.push(nums[current]);
    				}
    			} else if (sum < 0) {
    				if (nums[current] - intSt.get(stacksize-1) > 0) {
    					intSt.push(nums[current]);
    				}
    			}
    			
    		}
    	}
    	
    	for (int i = 0; i < intSt.size(); i++) {
    		System.out.printf("%d,", intSt.get(i));
    	}
    	
    	System.out.printf("\n");
    	
		return intSt.size();

    }
    
    public static void main(String args[]) { 
    	int[] num = new int[]{33,53,12,64,50,41,45,21,97,35,47,92,39,0,93,55,40,46,69,42,6,95,51,68,72,9,32,84,34,64,6,2,26,98,3,43,30,60,3,68,82,9,97,19,27,98,99,4,30,96,37,9,78,43,64,4,65,30,84,90,87,64,18,50,60,1,40,32,48,50,76,100,57,29,63,53,46,57,93,98,42,80,82,9,41,55,69,84,82,79,30,79,18,97,67,23,52,38,74,15};
    	Solution sol = new Solution();
    	int szie = sol.wiggleMaxLength(num);
    	
    	System.out.printf("The max length is %d\n", szie);
    }
}
