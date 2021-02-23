package lengthOfLongestSubstring;

import java.util.HashSet;
import java.util.Set;

public class Solution {
	public int lengthOfLongestSubstring(String s) {
		
		int maxLongth = 0;
		int currentLongth = 0;
		int begin = 0;
				
		if (s.isEmpty()) {
			return 0;
		}
		
		Set<Character> charSet = new HashSet<>();
		
		for (int i = 0; i < s.length(); i++) {
			
			if(!charSet.contains(s.charAt(i))) {
				currentLongth++;
				charSet.add(s.charAt(i));
			} else {
				
				if (currentLongth > maxLongth) {
					maxLongth = currentLongth;
				}
				
				for (int k = begin; k < i; k++) {
					charSet.remove(s.charAt(k));				
					currentLongth--;
					if(!charSet.contains(s.charAt(i))) {
						begin = k + 1;	
						charSet.add(s.charAt(i));
						currentLongth++;
						break;
					}
					
				}
			}
		}
		
		if (currentLongth > maxLongth) {
			maxLongth = currentLongth;
		}
		
		return maxLongth;
	}
	
	public static void main (String[] args) {
		String s = "pwwkew";
		Solution solution = new Solution();
		System.out.println(solution.lengthOfLongestSubstring(s));
	}
}
