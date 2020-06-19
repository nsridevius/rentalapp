package rentalapp;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class RentalCarServiceDemo {

	public static void main(String[] args) throws Exception {
		
		LocalDate date = LocalDate.now();
		LocalDateTime today = LocalDateTime.of(date.getYear(),date.getMonth(),date.getDayOfMonth(),0,0);
		LocalDateTime tomorrow = today.plusDays(1);
		LocalDateTime dayAfterTomorrow = today.plusDays(2);
		LocalDateTime plus3Days = today.plusDays(3);
		
		/**
		 * we know our CarRepository has only 3 vans
		 * so let us reserve 4 vans , starting with tomorrow, dayAfter, after 3 days
		 * lastly we reserve one more just for today and we can check to see the reservation can be accommodated
		 */
		// puposely introdcuing sleep so timer gets a chance to run.
		// otherwise main finishes without timer getting kicked 
		RentalCarService rcs = RentalCarService.getInstance();
		String reg = rcs.reserveVehicle(CarType.VAN, tomorrow , 2);
		System.out.println("regsitration number " + reg);
		RentalCarService.getInstance().displayVehicleInfo(reg);
		RentalCarService.getInstance().displayQueueSizes();
		
		System.out.println("**********************");
		
		Thread.sleep(3000);
		reg = rcs.reserveVehicle(CarType.VAN, dayAfterTomorrow , 5);
		System.out.println("regsitration number " + reg);
		RentalCarService.getInstance().displayVehicleInfo(reg);
		RentalCarService.getInstance().displayQueueSizes();
		
		System.out.println("**********************");
		
		Thread.sleep(3000);
		reg = rcs.reserveVehicle(CarType.VAN, plus3Days , 10);
		System.out.println("regsitration number " + reg);
		RentalCarService.getInstance().displayVehicleInfo(reg);
		RentalCarService.getInstance().displayQueueSizes();
		
		System.out.println("**********************");
		Thread.sleep(3000);
		reg = rcs.reserveVehicle(CarType.VAN, today , 1);
		System.out.println("regsitration number " + reg);
		RentalCarService.getInstance().displayVehicleInfo(reg);
		RentalCarService.getInstance().displayQueueSizes();
		
		System.out.println("**********************");
		Thread.sleep(3000);
		RentalCarService.getInstance().displayQueueSizes();
		
		// finally cancel timers and purge any tasks
		RentalCarService.getInstance().cancelTimers();
		
		System.out.println("DONE......");

	}

}
