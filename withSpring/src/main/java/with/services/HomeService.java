package with.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import with.Home;
import with.Room;

@Service
public class HomeService {

	double calculateRoomSuperficie(Room room) {

		return room.getLargeur() * room.getLongueur();
	}

	double calculateHomeSuperficie(Home home) {
		
		double superficie = 0;
		
		for (Room room : home.getRooms()) {
			
			superficie += calculateRoomSuperficie(room);
		}

		return superficie;
	}
	
	double calculatePrice(@Value("${prix.M2}") double prix ,Home home){
		
		return calculateHomeSuperficie(home) * prix;
	}
	
//	String infos() {
//		
//	}

}
