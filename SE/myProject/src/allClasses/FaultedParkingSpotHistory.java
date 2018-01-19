package allClasses;

public class FaultedParkingSpotHistory {

	@Override
	public String toString() {
		return "FaultedParkingSpotHistory [parkingLot=" + parkingLot + ", row=" + row + ", column=" + column
				+ ", width=" + width + ", date=" + date + "]";
	}
	String parkingLot;
	int row;
	int column;
	int width;
	String date;
	
	
	public FaultedParkingSpotHistory(String parkingLot, int row, int column, int width, String date) {
		super();
		this.parkingLot = parkingLot;
		this.row = row;
		this.column = column;
		this.width = width;
		this.date = date;
	}
	public String getParkingLot() {
		return parkingLot;
	}
	public void setParkingLot(String parkingLot) {
		this.parkingLot = parkingLot;
	}
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getColumn() {
		return column;
	}
	public void setColumn(int column) {
		this.column = column;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	
	
	
}
