package with;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import with.services.HomeService;

@SpringBootApplication
public class WithSpringApplication {
	
	private final HomeService homeService;
	
	public WithSpringApplication(HomeService homeService) {
		super();
		this.homeService = homeService;
	}
	public static void main(String[] args) {
		SpringApplication.run(WithSpringApplication.class, args).getBean(HomeService.class);
		
	}
	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() {
	    System.out.println("hello world, I have just started up");
	    
	   

		Home home = new Home();
		Room room1 = new Room();
		Room room2 = new Room();
		
		room1.setLargeur(15);
		room1.setLongueur(20);
		room2.setLargeur(10);
		room2.setLongueur(30);
		
		List<Room> rooms = new ArrayList<>();
		
		rooms.add(room1);
		rooms.add(room2);
		
		home.setRooms(rooms);
		
		System.out.println(homeService.);
		
	}

}
