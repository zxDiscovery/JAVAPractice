
public class quickSort {

	public static void main(String[] args) {
		
		int[] nums = new int[100];
		
		for (int i = 0; i < 100; i++) {
			nums[i] = (int)(Math.random()*100)+1;
		}
		
		
		quickSort qsort = new quickSort();
		int start = 0;
		int end = nums.length -1;
		
		qsort.qSort(nums, start, end);
		
		for (int num: nums) {
			System.out.println(num);
		}
		
	}
	
	public int[] qSort(int[] arr, int start, int end) {
	    int pivot = arr[start];        
	    int i = start;        
	    int j = end;        
	    while (i<j) {            
	        while ((i<j)&&(arr[j]>pivot)) {                
	            j--;            
	        }            
	        while ((i<j)&&(arr[i]<pivot)) {                
	            i++;            
	        }            
	        if ((arr[i]==arr[j])&&(i<j)) {                
	            i++;            
	        } else {                
	            int temp = arr[i];                
	            arr[i] = arr[j];                
	            arr[j] = temp;            
	        }        
	    }        
	    if (i-1>start) arr=qSort(arr,start,i-1);        
	    if (j+1<end) arr=qSort(arr,j+1,end); 
	    
	    return arr;
	}

}

