package scannerdome;

import java.util.Scanner;

public class Input {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		
		while (true) {
			
			String line = s.nextLine();
			if (line.equals("exit")) {
				break;
			}
			
			System.out.println(">>>>:"+line);
			
		}
		System.out.println("exit....");
		
		
	}
}
