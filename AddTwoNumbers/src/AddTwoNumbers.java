import java.util.Random;

public class AddTwoNumbers {
	
	public ListNode addTwoNumbers (ListNode l1, ListNode l2) {
		
		int carry = 0;
		int number = 0;
		int index = 0;
		
		ListNode lr = new ListNode();
		ListNode cur = lr;

		while(l1 != null || l2 != null) {
			
			if (l1 != null && l2 != null) {
				number = l1.val + l2.val + carry;
				
				if (number >= 10) {
					number = number - 10;
					carry = 1;
				} else {
					carry = 0;
				}
				l1 = l1.next;
				l2 = l2.next;
			} else if (l1 != null && l2 == null) {
				number = l1.val + carry;
				if (number >= 10) {
					number = number - 10;
					carry = 1;
				} else {
					carry = 0;
				}
				l1 = l1.next;
			} else if (l1 == null && l2 != null) {
				number = l2.val + carry;
				if (number >= 10) {
					number = number - 10;
					carry = 1;
				} else {
					carry = 0;
				}
				l2 = l2.next;
			}
			
			if (index == 0) {
				lr.val = number;
			} else {
				lr.next = new ListNode(number);
				lr = lr.next;
			}
			
			index++;
			
		}
		
		if (carry == 1) {
			lr.next = new ListNode(carry);
			lr = lr.next;
		}
		
		return cur;
	}
	
	public static void main(String[] args) {
		
		Random random = new Random();
		int n = 0;
		int m = 0;
		
		ListNode l1 = new ListNode(1);
		ListNode l2 = new ListNode(9);
		ListNode cur1 = l1;
		ListNode cur2 = l2;
		
		while (n < 0) {
			n++;
			cur1.next = new ListNode(random.nextInt(10));
			cur1 = cur1.next;
		}
		
		while (m < 3) {
			m++;
			cur2.next = new ListNode(9);
			cur2 = cur2.next;
		}
		
		/*
		ListNode l1 = new ListNode(random.nextInt(10));
		ListNode l2 = new ListNode(random.nextInt(10));
		ListNode cur1 = l1;
		ListNode cur2 = l2;
		
		while (n < random.nextInt(10)) {
			n++;
			cur1.next = new ListNode(random.nextInt(10));
			cur1 = cur1.next;
		}
		
		while (m < random.nextInt(10)) {
			m++;
			cur2.next = new ListNode(random.nextInt(10));
			cur2 = cur2.next;
		}
		
		System.out.println("L1 is: " + (n+1) + "; L2 is:" + (m + 1));
		*/
		
		cur1 = l1;
		while (cur1 != null) {
			System.out.print(cur1.val + ", ");
			cur1 = cur1.next;
		}
		
		System.out.println();
		
		cur2 = l2;
		while (cur2 != null) {
			System.out.print(cur2.val + ", ");
			cur2 = cur2.next;
		}
		
		System.out.println();
		
		AddTwoNumbers an = new AddTwoNumbers();
		
		ListNode l3 = an.addTwoNumbers(l1, l2);
		
		while (l3 != null) {
			System.out.print(l3.val + ", ");
			l3 = l3.next;
		}
		
		System.out.println();
		
		while (l1 != null) {
			System.out.print(l1.val + ", ");
			l1 = l1.next;
		}
		
	}
	
}
