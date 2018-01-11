package allClasses;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

//import java.time.*;
//import java.sql.Time;;

public class Complaint {

	String ParkingLot;
	String customerId;
	String complaintText;
	boolean isChecked;
	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	String submissionDate;


	@SuppressWarnings("deprecation")
	public Complaint(String parkingLot, String customerId, String text) {
		super();
		ParkingLot = parkingLot;
		this.customerId = customerId;
		this.complaintText = text;
		this.isChecked = false;
		Date date=new Date();
		this.submissionDate=sdf.format(date);
	
	}

	public String getParkingLot() {
		return ParkingLot;
	}

	public void setParkingLot(String parkingLot) {
		ParkingLot = parkingLot;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getComplaintText() {
		return complaintText;
	}

	public void setComplaintText(String complaintText) {
		this.complaintText = complaintText;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}





	public static void main(String[] args) {
		Complaint c = new Complaint("a", "aaa", "yes");
		System.out.println(c);
	}

	public String getSubmissionDate() {
		return submissionDate;
	}

	public void setSubmissionDate(String submissionDate) {
		this.submissionDate = submissionDate;
	}

	@Override
	public String toString() {
		return "Complaint [ParkingLot=" + ParkingLot + ", customerId=" + customerId + ", complaintText=" + complaintText
				+ ", isChecked=" + isChecked + ", submissionDate=" + submissionDate + "]";
	}

}
