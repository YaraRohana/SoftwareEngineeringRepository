package allClasses;

public class ParkingLot {

	int id;
	String name;
	String location;
	boolean isActive;
	boolean available;
	String manager;

	public ParkingLot(int id, String name, String location, boolean isActive,
			boolean available, String manager) {
		super();
		this.id = id;
		this.name = name;
		this.location = location;
		this.isActive = isActive;
		this.available = available;
		this.manager = manager;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}
}
