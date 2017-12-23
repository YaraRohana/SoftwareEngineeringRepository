package allClasses;

import java.util.ArrayList;

public class ParkingLot {

	int id;
	String name;
	String location;
	boolean isActive;
	boolean isFull;
	String manager;
	int width;

	public ParkingLot( String name, String location, boolean isActive, boolean isFull, String manager,
			int width) {
		super();
		this.name = name;
		this.location = location;
		this.isActive = isActive;
		this.isFull = isFull;
		this.manager = manager;
		this.width = width;
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

	public boolean isFull() {
		return isFull;
	}

	public void setFull(boolean isFull) {
		this.isFull = isFull;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	@Override
	public String toString() {
		return "ParkingLot [id=" + id + ", name=" + name + ", location=" + location + ", isActive=" + isActive
				+ ", isFull=" + isFull + ", manager=" + manager + ", width=" + width + "]";
	}

}
