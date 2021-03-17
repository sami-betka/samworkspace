package javaExercices;

public class User {
	
	private String name;
	private int age;
	private User godfather;

	
	public User() {
		super();
	}
	
	public User(String name, int age, User godfather) {
		super();
		this.name = name;
		this.age = age;
		this.godfather = godfather;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}

	public User getGodfather() {
		return godfather;
	}

	public void setGodfather(User godfather) {
		this.godfather = godfather;
	}

}
