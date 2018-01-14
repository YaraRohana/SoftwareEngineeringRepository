package allClasses;

public class ParkingSpot {

	boolean isOccupied;
	boolean isFaulted;
	boolean isSaved;
	
	public ParkingSpot(boolean isOccupied, boolean isFaulted, boolean isSaved) {
		super();
		this.isOccupied = isOccupied;
		this.isFaulted = isFaulted;
		this.isSaved = isSaved;
	}
	public boolean isSaved() {
		return isSaved;
	}
	public void setSaved(boolean isSaved) {
		this.isSaved = isSaved;
	}
	public boolean isOccupied() {
		return isOccupied;
	}
	public void setOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
	}
	public boolean isFaulted() {
		return isFaulted;
	}
	public void setFaulted(boolean isFaulted) {
		this.isFaulted = isFaulted;
	}
	
}

