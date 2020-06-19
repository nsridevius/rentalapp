package rentalapp;

import java.time.LocalDateTime;

/**
 *  A simple interface defining rental actions that RentalCarService could use
 *  for now, cancelReservation is not implemented 
 *
 */
public interface RentalAction {
	
	public void reserveVehicle(CarType type, LocalDateTime start, int days);
	public void cancelReservation(String regNumber);

}
