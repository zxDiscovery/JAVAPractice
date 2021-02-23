import java.util.concurrent.Callable;
import java.util.function.Function;

public class LambdaTest {
	
	public static void main(String[] args) {

		Callable<String> runFuction = ()-> "Lambda run";
		try {
			System.out.println(runFuction.call());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		StudentDao sd1 = new StudentDao() {
			
			@Override
			public void insert(Student student) {
				System.out.println("Inset the student 1");
			}
		};
		
		sd1.insert(new Student());
		
		StudentDao sd2 = (student)->{
			System.out.println("student: " + student);
		};
		
		sd2.insert(new Student());
		
		StudentDao sd3 = (student) -> System.out.println("student: "+ student);
		
		sd3.insert(new Student());
		
		Function<String, Integer> f1 = (str) -> {return str.length();};
		System.out.println(f1.apply("1234567"));
	}
}
