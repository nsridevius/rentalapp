package rentalapp;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RentalCarServiceTest {
	
	static RentalCarService rcs = null;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		rcs = RentalCarService.getInstance();
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		rcs.cancelTimers();
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testReserveVehicle() throws InterruptedException {
		//fail("Not yet implemented");
		
		LocalDate date = LocalDate.now();
		LocalDateTime today = LocalDateTime.of(date.getYear(),date.getMonth(),date.getDayOfMonth(),0,0);
		LocalDateTime tomorrow = today.plusDays(1);
		LocalDateTime dayAfterTomorrow = today.plusDays(2);
		LocalDateTime plus3Days = today.plusDays(3);

		String reg = rcs.reserveVehicle(CarType.VAN, tomorrow, 2);
		RentalCarService.getInstance().displayVehicleInfo(reg);
	    assertNotEquals("",reg);
	    Thread.sleep(2000);
	    assertEquals(1,rcs.getReservedQueueCount());
	    RentalCarService.getInstance().displayQueueSizes();
	    
	    reg = rcs.reserveVehicle(CarType.VAN, dayAfterTomorrow , 5);
	    RentalCarService.getInstance().displayVehicleInfo(reg);
	    assertNotEquals("",reg);
	    Thread.sleep(3000);
	    assertEquals(2,rcs.getReservedQueueCount());
	    RentalCarService.getInstance().displayQueueSizes();
	    
	    reg = rcs.reserveVehicle(CarType.VAN, plus3Days , 10);
	    RentalCarService.getInstance().displayVehicleInfo(reg);
	    assertNotEquals("",reg);
	    Thread.sleep(3000);
	    assertEquals(3,rcs.getReservedQueueCount());
	   
	    //no more available, let us see if reuse can happen
	    // for now no partial hour support. a sngle day will use 0 for days
	    reg = rcs.reserveVehicle(CarType.VAN, today , 1);
	    RentalCarService.getInstance().displayVehicleInfo(reg);
	    assertNotEquals("",reg);
	    assertEquals(4,rcs.getReservedQueueCount());
		Thread.sleep(3000);
		assertEquals(3,rcs.getReservedQueueCount());
		assertEquals(1,rcs.getRentedQueueCount());
	    
		RentalCarService.getInstance().displayQueueSizes();
	}

}
