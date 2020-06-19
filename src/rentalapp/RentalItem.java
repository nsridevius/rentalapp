package rentalapp;

import java.time.LocalDateTime;

/**
 * RentalItem that holds the vehicle, startDate and number of days to rent
 * For simplicity, not implementing Comparable interface as no two vehicles are going to be same
 * Each has its own regsitration number and we can use custom comparator when working with these items
 *
 */
public class RentalItem {
	
	private Vehicle vehicle;
	private LocalDateTime startDateTime;
	private LocalDateTime endDateTime;
	private int days;
	
	public RentalItem(Vehicle v, LocalDateTime startDateTime, int days) {
		this.vehicle = v;
		this.startDateTime = startDateTime;
		this.setDays(days);
		this.endDateTime = this.startDateTime.plusDays(days);
	}
	
	public LocalDateTime getStartDateTime() {
		return this.startDateTime;
	}
	
	public LocalDateTime getEndDateTime() {
		return this.endDateTime;
	}
	
	public boolean checkForRunning() {
		LocalDateTime now = LocalDateTime.now();
		
		if( now.isEqual(startDateTime) || now.isAfter(startDateTime))
			return true;
		
		return false;
	}
	
	public boolean checkForReturning() {
		LocalDateTime now = LocalDateTime.now();
		
		if( now.isEqual(endDateTime) || now.isAfter(endDateTime))
			return true;
		
		return false;
	}
	
	public void updateState(RentalState.State newState) {
		((Car)vehicle).updateState(newState);
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}
	
	public Vehicle getVehicle() {
		return this.vehicle;
	}
	
	public String toString() {
		StringBuilder bldr = new StringBuilder();
		bldr.append(  ((Car)vehicle).toString() );
		bldr.append( " BETWEEN " + startDateTime + " AND " + endDateTime);
		return bldr.toString();
	}
  
}
