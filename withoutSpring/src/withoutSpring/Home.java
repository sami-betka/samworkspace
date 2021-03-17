package withoutSpring;

import java.util.List;

public class Home {

	private String name;
	private List<Room> rooms;

	public Home(String name, List<Room> rooms) {
		super();
		this.name = name;
		this.rooms = rooms;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Room> getRooms() {
		return rooms;
	}

	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}

}
