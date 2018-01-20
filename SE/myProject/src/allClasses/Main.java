package allClasses;

import java.io.File;

public class Main {

	public static void main(String[] args) {
	/*	CPS cps=CPS.getInstance();
		ParkingLot pl=new ParkingLot("University Of Haifa", "Haifa", true, false, "Yara Rohana", 2);
		ParkingLot pl2=new ParkingLot("Romema", "Haifa", true, false, "Naya", 2);
		cps.getParkingLots().add(pl);
		cps.getParkingLots().add(pl2);
		for (ParkingLot i : cps.getParkingLots()) {
			System.out.println(i.toString());
		}*/
		
			try {
				ParkingLot parking = new ParkingLot("Univ", "location", true, false, "wasim", 12);
				parking.setFaultedParkingSpot(2, 2, 5);
				parking.setOccupiedParkingSpot(1, 1, 9);
				parking.setSavedParkingSpot(0, 0, 0);
			
				CreatePdf pdf = new CreatePdf(parking,"results" + File.separator + "objects" + File.separator + "newPDF.pdf");
				pdf.create();
			
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
		
	}

}
