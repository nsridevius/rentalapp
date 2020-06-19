package rentalapp;

import java.util.UUID;

/**
 * Abstract vehicle implements vehicle. follows Open / Closed principle
 * closed for modifications, but open for extensions/ derivatives
 * 
 */
public abstract class AbstractVehicle implements Vehicle {

	private String registrationNumber;	
	
	public AbstractVehicle() {
		// for simplicity we use a random UUID for now, replacing all hyphens
		 String uuid = UUID.randomUUID().toString();
		 setRegistrationNumber(uuid.replace("-", ""));		 
	}
		
	@Override
	public String getRegistrationNumber() {
		return this.registrationNumber;
	}
	
	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

}
