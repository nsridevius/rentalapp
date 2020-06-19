package rentalapp;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * temporary repository of cars, instead of a database let us have inmemory
 * Implemented as singleton 
 *
 */
public class CarRepository {
	//let this be our in memory cache database for now
	private ConcurrentHashMap<String , Vehicle> allCars = new ConcurrentHashMap<>();
	//EAGER THREAD SAFE INITIALIZATION
	private static final CarRepository INSTANCE = new CarRepository();
		
	private CarRepository() {
		Vehicle v = new Car(CarMake.TOYOTA.getValue(),
					CarModel.CAMRY.getValue(),CarType.SEDAN.getValue(), 2000);
		allCars.putIfAbsent( v.getRegistrationNumber(), v);
			
		v = new Car(CarMake.TOYOTA.getValue(),
					CarModel.COROLLA.getValue(),CarType.SEDAN.getValue(), 2000);
		allCars.putIfAbsent( v.getRegistrationNumber(), v);
			
		v = new Car(CarMake.TOYOTA.getValue(),
					CarModel.PRIUS.getValue(),CarType.SEDAN.getValue(), 2000);
		allCars.putIfAbsent( v.getRegistrationNumber(), v);
			
		v = new Car(CarMake.HONDA.getValue(),
					CarModel.CIVIC.getValue(),CarType.SEDAN.getValue(), 2000);
		allCars.putIfAbsent( v.getRegistrationNumber(), v);
			
		v = new Car(CarMake.SUBARU.getValue(),
					CarModel.OUTBACK.getValue(),CarType.SEDAN.getValue(), 2000);
		allCars.putIfAbsent( v.getRegistrationNumber(), v);
			
		v = new Car(CarMake.FORD.getValue(),
					CarModel.MUSTANG.getValue(),CarType.SEDAN.getValue(), 2000);
		allCars.putIfAbsent( v.getRegistrationNumber(), v);
			
		// SUV's
		v = new Car(CarMake.SUBARU.getValue(),
					CarModel.FORESTER.getValue(),CarType.SUV.getValue(), 2000);
		allCars.putIfAbsent( v.getRegistrationNumber(), v);
		
		v = new Car(CarMake.TOYOTA.getValue(),
					CarModel.RAV4.getValue(),CarType.SUV.getValue(), 2000);
		allCars.putIfAbsent( v.getRegistrationNumber(), v);
		
		v = new Car(CarMake.HONDA.getValue(),
					CarModel.CRV.getValue(),CarType.SUV.getValue(), 2000);
		allCars.putIfAbsent( v.getRegistrationNumber(), v);
		
		v = new Car(CarMake.HONDA.getValue(),
					CarModel.ACURA.getValue(),CarType.SUV.getValue(), 2000);
			allCars.putIfAbsent( v.getRegistrationNumber(), v);
			
		//vans
		v = new Car(CarMake.TOYOTA.getValue(),
					CarModel.SIENNA.getValue(),CarType.VAN.getValue(), 2000);
		allCars.putIfAbsent( v.getRegistrationNumber(), v);
		
		v = new Car(CarMake.HONDA.getValue(),
					CarModel.ODYSSEY.getValue(),CarType.VAN.getValue(), 2000);
		allCars.putIfAbsent( v.getRegistrationNumber(), v);
			
		v = new Car(CarMake.DODGE.getValue(),
					CarModel.CARAVAN.getValue(),CarType.VAN.getValue(), 2000);
		allCars.putIfAbsent( v.getRegistrationNumber(), v);
	}
		
	public static CarRepository getInstance() {
		return INSTANCE;
	}

	public Vehicle findUnreservedCar(CarType type) {
			
		Collection<Vehicle> vehicles = allCars.values();
		List<Vehicle> matchingUnreservedTypes = 
				vehicles.stream().filter( v -> v.getType().equals(type.getValue())  ).filter(v -> v.isUnreserved()).collect(Collectors.toList());
			
		if( !matchingUnreservedTypes.isEmpty())
			return matchingUnreservedTypes.get(0);
			
		return null;
	}
		
	public Vehicle getCar(String regNumber) {
		return allCars.get(regNumber);
	}
		
}
