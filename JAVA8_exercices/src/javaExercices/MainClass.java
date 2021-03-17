package javaExercices;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class MainClass<T> {

	public static List<List<String>> splitBatch(List<String> list, int n) {

		int nbBatch = (list.size() + n - 1 ) /n;

		List<List<String>> result = new ArrayList<>();

		for (int i = 0; i < nbBatch; i++) {
			result.add(list.subList(i * nbBatch, Math.min((i + 1) * nbBatch, list.size())));
		}

		return result;
	}

	public static <T> List<List<T>> modifiedSplitBatch(List<T> list, int n) {

		int nbBatch = (list.size() + n - 1 ) /n;

		List<List<T>> result = new ArrayList<>();

		for (int i = 0; i < nbBatch; i++) {
			result.add(list.subList(i * nbBatch, Math.min((i + 1) * nbBatch, list.size())));
		}

		return result;
	}
	
	
	@FunctionalInterface
	public interface SumFunction {

	    int sum(int num1, int num2, int num3);

	}
	
	public static List<User> createUserList(){
		
	List<User> users = new ArrayList<>();
		
		User one = new User();
		User two = new User();
		User three = new User();
		User four = new User();
		User five = new User();
		User six = new User();
		User seven = new User();
		User eight = new User();
		
		one.setAge(25);
		one.setName("Jerome");
		one.setGodfather(two);
		
		two.setAge(34);
		two.setName("Jerome");
		two.setGodfather(three);
		
		three.setAge(45);
		three.setName("Bob");
		three.setGodfather(four);
		
		four.setAge(55);
		four.setName("Toto");
		four.setGodfather(five);
		
		five.setAge(65);
		five.setName("Alain");
		
		six.setAge(39);
		six.setName("Sam");
		
		seven.setAge(16);
		seven.setName("Jean");
		
		eight.setAge(45);
		eight.setName("Jacques");
		
		users.add(one);
		users.add(two);
		users.add(three);
		users.add(four);
		users.add(five);
		users.add(six);
		users.add(seven);
		users.add(eight);
		
		return users;
	}
	
	

	public static void main(String[] args) {
		
	
	List<User> users = createUserList();
	
	users.stream()
	.filter(u -> !u.getGodfather().equals(null))
	.collect(Collectors.averagingInt(mapper));
		
		
	}
}
