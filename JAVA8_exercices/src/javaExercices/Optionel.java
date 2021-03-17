package javaExercices;

import java.util.function.Consumer;

public class Optionel<T> {

	private T instance;

	private Optionel(T instance) {
		super();
		this.instance = instance;
	}

	public static <T> Optionel<T> of(T instance) {

		return new Optionel<T>(instance);
	}

	public void ifPresent(Consumer<T> c) {
		
		if(isPresent()) {
			c.accept(instance);
		}
					
	}

	public boolean isPresent() {

		return instance != null;
	}

	public T get() {

		if (!isPresent()) {
			throw new RuntimeException("Pas bien !");
		}

		return instance;
	}

}
