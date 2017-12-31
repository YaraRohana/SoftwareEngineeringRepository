package allClasses;

public abstract class Employee {

	String name;
	String password;	
	
	public enum employeeType{
		parkingLotManager(0),companyManager(1),customerServiceEmployee(2),parkingLotEmployee(3);
		
		private final int value;
	    private employeeType(int value) {
	        this.value = value;
	    }

	    public int getValue() {
	        return value;
	    }
	}
}

