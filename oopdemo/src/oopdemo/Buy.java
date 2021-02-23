package oopdemo;

public class Buy {

	public Pet buyPet(int type) {
		if (type == 1) {
			return new Dog();
		} else if (type == 2) {
			return new Cat();
		}
		
		return null;
	}
	
}
