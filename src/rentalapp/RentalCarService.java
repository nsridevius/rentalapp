package rentalapp;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Timer;
import java.util.TimerTask;

/**
 * RentalCarService, service class again implemented as singleton for simplicity 
 * This class uses priority queues for handling reserved and rented ones 
 *
 */
public class RentalCarService {
	
	private static final RentalCarService INSTANCE = new RentalCarService();
	private static Object lock = new Object();
	 
	Timer reservedVehiclesMonitor = new Timer();
	Timer rentedVehiclesMonitor = new Timer();
		
	// A custom comparator that compares two rentals on the reserved queue.
	// scheduled rentals will be compared based off start date and time
	// all vehicles will be in single queue ordered by their start time
    Comparator<RentalItem> startDateTimeComp = new Comparator<RentalItem>() {
        @Override
        public int compare(RentalItem i1, RentalItem i2) {
            return i1.getStartDateTime().compareTo(i2.getStartDateTime());
        }
    };
    
    // we will use this comparator for vehicles on road or rented and currently running
    Comparator<RentalItem> endDateTimeComp = new Comparator<RentalItem>() {
        @Override
        public int compare(RentalItem i1, RentalItem i2) {
            return i1.getEndDateTime().compareTo(i2.getEndDateTime());
        }
    };
        
    //order the elements in the queue in the order of start date/time for renting.
    // so head of the element can be peeked for readiness of running
    // for now let us use unbounded queue. although we know vehicles of each type are limited
    // Same vehicle can be rented twice if they are of same type and start/end times do not overlap
    // the size can be more than the total number of vehicles
	private PriorityQueue<RentalItem> reservedVehicles = new PriorityQueue<RentalItem>(startDateTimeComp);
	
	//order the elements in the queue in the order of end date/time for renting.
    // so head of the element can be peeked for when it is goingt o be returned
	// this again for now is unbounded. technically this queue size will be total vehicles if all are rented off
	private PriorityQueue<RentalItem> rentedVehicles = new PriorityQueue<RentalItem>(endDateTimeComp);
	
	 class ReserveCheck extends TimerTask 
	    { 	       
	        public void run() 
	        { 	               	 
	            // make sure to lock as the head can change if a new reservation can come.
	            synchronized( lock)
	            {
	            	while (!reservedVehicles.isEmpty()) {
	            			 
	            		 RentalItem E = reservedVehicles.peek();
	            		 if( E == null)
	            			 continue;
	            		 //remember this is a priority queue sorted based on the start date
	            		 // if the head is not ready for rental, no need of inspecting the rest
	            		 if ( !E.checkForRunning() )
	            			 break;
	                     // make the state change to rented and move out of this queue
	            		 E = reservedVehicles.remove();
	            		 E.updateState(RentalState.State.RENTED);
	            		 System.out.println(E.getVehicle().toString());
	            		 rentedVehicles.add( new RentalItem(E.getVehicle(),E.getStartDateTime(),E.getDays()));
	            	 }	                
	            }
	        } 
	    } 
	
	 class RentedCheck extends TimerTask 
	    { 	       
	        public void run() 
	        { 
	             while (!rentedVehicles.isEmpty()) {
	                RentalItem E = rentedVehicles.peek();
	                if( E == null )
	                	continue;
	                //remember this is a priority queue sorted based on the end date
	                // if the head is not ready for rental, no need of inspecting the rest
	                if( !E.checkForReturning())
	                	break;
	                // make the state change to rented and move out of this queue
	                E.updateState(RentalState.State.UNRESERVED);
	                System.out.println(E.getVehicle().toString());
	                //remove from the rented queue as well
	                E = rentedVehicles.poll();
	                
	            }
	        } 
	    } 

	private RentalCarService() {
		//for now schedule the timers to wakeup every 1 sec
		reservedVehiclesMonitor.schedule(new ReserveCheck(), 0L , 1000L);
		rentedVehiclesMonitor.schedule(new RentedCheck(), 1L , 1000L);
	}
		
	/**
	 * Reserve vehicle request comes from client asking for CarType, start date/time and number of days
	 * @param type
	 * @param start
	 * @param days
	 * @return
	 */
	public String reserveVehicle(CarType type, LocalDateTime start, int days) {
		
		String regNum = "";
		boolean added = false;
		try
		{
			// ask the repository to find an unreserved vehicle given type
			Vehicle v = CarRepository.getInstance().findUnreservedCar(type);
		
			if( v != null ) {
				Car c = ((Car)v);
				c.updateState(RentalState.State.RESERVED);
				RentalItem	r = new RentalItem(c,start,days);
				synchronized( lock) {
					added = reservedVehicles.offer(r);
				}
				if( added )
					regNum =  v.getRegistrationNumber();
			}
			else {
				// no vehicle of given type in unreserved state. check if we could use an already reserved vehicle
				// if the start and end date/time's do not overlap
				
				regNum = findMatchingCarTypeFromReservedQueue(type,start,days);				
			}
		}
		catch( Exception e) {
			// possible classcast, nullpointer etc..
		}
		// return the vehicle registration number for renting
		return regNum;
	}
	
	/**
	 * method that iterates and peeks
	 * @param type
	 * @param start
	 * @param days
	 * @return
	 */
	public String findMatchingCarTypeFromReservedQueue(CarType type, LocalDateTime start, int days) {
		 boolean added = false;
		 String reg = "";
		 
		 Iterator<RentalItem> itr = reservedVehicles.iterator();
		 // the above iterator will not guarantee the order. 
		 // we are only interested in inspecting and not manipulating. 
		 // lock just in case new reservations get added        	 
        	 synchronized( lock)
        	 {
        		 while ( itr.hasNext()) {
                	 		 
        			 RentalItem E = itr.next();
        			 if( E == null ) 
        				 continue;
        			 if( !E.getVehicle().getType().equals(type.getValue()) )
        				 continue;
        			
        			 System.out.println(E);
        			 //  startDateTime1 <= startDateTime2 and endDateTime1 < endDateTime2 indicates no overlap
        			 LocalDateTime end = start.plusDays(days);
        			 boolean beforeOrEquals = start.isBefore(E.getStartDateTime()) || start.equals(E.getStartDateTime() );
        			 if( beforeOrEquals && end.isBefore(E.getStartDateTime()) ) {
        				 // we can schedule the same vehicle for rental
        				 System.out.println("found a resreved vehicle with no time overlaps for reuse");
        				RentalItem r = new RentalItem(E.getVehicle(),start,days);
        				added = reservedVehicles.offer(r);
        				if( added)
         					reg = E.getVehicle().getRegistrationNumber();
        				
        			 }
        			      				 
        			 //we can break; after finding the first vehicle of same type on queue
        			 if( added)
        				 break;
        			 
        		 }
        		 if( !added)
        			 System.out.println("could not accomodate the request for " + type + " starting at " + start + " for " + days);
        	 }
		 
		return reg;
	}

	public void displayVehicleInfo(String regNumber) {
		Vehicle v = CarRepository.getInstance().getCar(regNumber);
		if( v != null )
			System.out.println(v.toString());
		else
			System.out.println("The vehicle with empty registration number " + regNumber + " not in the repository");
	}
	
	public int getReservedQueueCount() {
		return reservedVehicles.size();
	}
	public int getRentedQueueCount() {
		return rentedVehicles.size();
	}
	
	public void displayQueueSizes() {
		System.out.println(" RESERVED QUEUE COUNT " + reservedVehicles.size());
		System.out.println(" RENTED QUEUE COUNT " + rentedVehicles.size());
	}
	public static RentalCarService getInstance() {
		return INSTANCE;
	}
	public void cancelTimers() {
		if( reservedVehiclesMonitor != null ) {
			reservedVehiclesMonitor.cancel();
			reservedVehiclesMonitor.purge();
		}
		if( rentedVehiclesMonitor != null ) {
			rentedVehiclesMonitor.cancel();
			rentedVehiclesMonitor.purge();
		}
	}
}