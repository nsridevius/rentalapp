package rentalapp;

public interface Vehicle {

 	// make = toyota, honda, subaru etc
	public String getMake();
	// model = camry, corolla, forrester, outback
	public String getModel();
	// sedan, SUV, van
	public String getType();
	// year of manufacture
	public int getYear();
	// unique registration number for each vehicle
	public String getRegistrationNumber();
	boolean isRented();
	boolean isReserved();
	boolean isUnreserved();	
}
