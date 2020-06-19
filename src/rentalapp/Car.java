package rentalapp;

public class Car extends AbstractVehicle {
	
	private String make;
	private String model;
	private String type = "Unknown";
	private int year = 0;	
	// Single responsibility , Car is a POJO. Its state is handled in a different class via composition
	// CAR HAS A STATE
	private RentalState state;
	
	public Car(String make, String model, String type, int year) {
		super();
		setMake(make);
	    setModel(model);
	    setType(type);
	    setYear(year);
	    this.state = new RentalState();	    
	}
	
	public void setMake(String make) {
		this.make = make;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public void setType(String type) {
		this.type = type; 
	}
	public void setYear(int year) {
		this.year = year;
	}
	@Override
	public String getMake() {
		return this.make;
	}

	@Override
	public String getModel() {
		return this.model;
	}
	@Override
	public String getType() {
		return this.type;
	}
	@Override
	public int getYear() {
		return this.year;
	}
	@Override
	public boolean isRented() {
		return getState().isRented();
	}
	@Override
	public boolean isReserved() {
		return getState().isReserved();
	}
	@Override
	public boolean isUnreserved() {
		return getState().isUnreserved();
	}
	public RentalState getState() {
		return state;
	}
	public void updateState(RentalState.State newState) {
		this.state.updateState(newState);
	}
	
	public String toString() {
		StringBuilder bldr = new StringBuilder();
		bldr.append(getMake() + " " + getModel() + " " + getType() + " Current State " + getState().getCurrentState());
		return bldr.toString();
	}

}
