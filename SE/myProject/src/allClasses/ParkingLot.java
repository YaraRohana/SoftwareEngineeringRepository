package allClasses;



public class ParkingLot {

	int id;
	String name;
	String location;
	boolean isActive;
	boolean isFull;
	String manager;
	int width;
	int canceledOrders;
	 ParkingSpot[][][] ParkingSpots;
	
	
	
	public int getCanceledOrders() {
		return canceledOrders;
	}

	public void setCanceledOrders(int canceledOrders) {
		this.canceledOrders = canceledOrders;
	}

	public ParkingLot( String name, String location, boolean isActive, boolean isFull, String manager,
			int width) {
		super();
		this.name = name;
		this.location = location;
		this.isActive = isActive;
		this.isFull = isFull;
		this.manager = manager;
		this.width = width;
		this.ParkingSpots= new ParkingSpot[3][3][width];
		for(int i=0;i<3;i++) {
			for(int j=0;j<3;j++) {
				for(int k=0;k<this.width;k++) {
					ParkingSpots[i][j][k]=new ParkingSpot(false,false,false);
				}
			}
		}
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

	public ParkingSpot[][][] getParkingSpots() {
		return ParkingSpots;
	}

	public void setParkingSpots(ParkingSpot[][][] parkingSpots) {
		ParkingSpots = parkingSpots;
	}

	@Override
	public String toString() {
		return "ParkingLot [id=" + id + ", name=" + name + ", location=" + location + ", isActive=" + isActive
				+ ", isFull=" + isFull + ", manager=" + manager + ", width=" + width + ", canceledOrders="
				+ canceledOrders + "]";
	}
	
	public void setFaultedParkingSpot(int row,int col,int width) {
		if(row>=0 && row<3 && col>=0 && row<3 && width<this.width) {
			ParkingSpots[row][col][width].setFaulted(true);
		}
	}

	public void unsetFaultedParkingSpot(int row,int col,int width) {
		if(row>=0 && row<3 && col>=0 && row<3 && width<this.width) {
			ParkingSpots[row][col][width].setFaulted(false);
		}
	}
	
	public void setOccupiedParkingSpot(int row,int col,int width) {
		if(row>=0 && row<3 && col>=0 && row<3 && width<this.width) {
			ParkingSpots[row][col][width].setOccupied(true);
		}
	}
	
	public void unsetOccupiedParkingSpot(int row,int col,int width) {
		if(row>=0 && row<3 && col>=0 && row<3 && width<this.width) {
			ParkingSpots[row][col][width].setOccupied(false);
		}
	}
	
	public  void setSavedParkingSpot(int row,int col,int width) {
		System.out.println("fotna 3l function");
		if(row>=0 && row<3 && col>=0 && col<3 && width<this.width) {
			System.out.println("fotna 3l if");
			System.out.println("in parkingLot");
			this.ParkingSpots[row][col][width].setSaved(true);
			System.out.println("here" +ParkingSpots[row][col][width].isSaved);
		}	
	}
	
	public void unsaveParkingSpot(int row,int col,int width) {
		if(row>=0 && row<3 && col>=0 && row<3 && width<this.width) {
			ParkingSpots[row][col][width].setSaved(false);
		}
	}

}
