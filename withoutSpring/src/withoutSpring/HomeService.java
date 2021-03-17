package withoutSpring;

public class HomeService {
	
	double roomSuperficie(Room room) {
		
			return room.getLargeur()*room.getLongueur();
	}
	
	double HomeSuperficie (Home home) {
		
		double superficie = 0;
		
		for(Room room : home.getRooms()) {
			superficie += roomSuperficie(room);
			
		}
		
		return superficie;
	}
	
//	double calcalutePrice () {	
//		
//		return ;
//	}

	
	
}
