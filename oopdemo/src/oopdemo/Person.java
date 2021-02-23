package oopdemo;

public class Person {

	public void feed(Pet p) {
		p.feed();
	}
	
	public static void main(String[] args) {
		Person person = new Person();
		
		Buy by = new Buy();
		
		Pet p = by.buyPet(1);
		
		person.feed(p);
	}
	
}
