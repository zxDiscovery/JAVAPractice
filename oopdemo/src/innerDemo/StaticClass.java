package innerDemo;

public class StaticClass {

	private int id;
	
    class InnerClass{
		private String name;
		
		public void show() {
			System.out.println("show");
		}
		
	}
	
	public static void main(String[] args) {
		InnerClass ic = new StaticClass().new InnerClass();
		
		ic.show();
	}
	
}
