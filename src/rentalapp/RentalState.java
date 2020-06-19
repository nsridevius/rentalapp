package rentalapp;

public class RentalState {
	public enum State
	{
	  UNRESERVED("Unreserved") ,
	  RESERVED("Reserved"),
	  RENTED("Rented");
	  
	  private final String value;
	  State(String value) {
		this.value = value;
	  }
	  public String getValue() {
			return value;
	  }
    }
	
	private State currentState = State.UNRESERVED;
	
	public State getCurrentState() {
		return this.currentState;
	}
	
	public static State getPrevState(State state) {
		State prev = State.UNRESERVED;
		
		switch(state) {
		case UNRESERVED:
			prev = State.UNRESERVED;
			break;
		case RESERVED:
			prev = State.UNRESERVED;
			break;
		case RENTED:
			prev = State.RESERVED;
			break;
		}
		
		return prev;
	}
	
	public static State getNextState(State state) {
		State next = State.UNRESERVED;
		
		switch(state) {
		case UNRESERVED:
			next = State.RESERVED;
			break;
		case RESERVED:
			next = State.RENTED;
			break;
		case RENTED:
			next = State.UNRESERVED;
			break;
		}
		
		return next;
	}
	
	//write validation code to validate the state transitions. leaving empty for now
	//UNRESERVED -> RESERVED - just when booked or scheduled
	//RESERVED -> RENTED    -> when actually ready to rent and ride
	//RESERVED -> UNRESERVED -> cancel the reservation or unbook
	//RENTED -> UNRESERVED -> finished renting and return to start state
	
	public static boolean validTransition(State from, State to) {
		return true;
	}
	
	public void updateState(State newState) {
		//System.out.println("updateState called with " + newState + " by " + Thread.currentThread().getName());
		this.currentState = newState;
	}
	
	public boolean isRented() {
		return this.currentState.equals(State.RENTED);
	}
	public boolean isReserved() {
		return this.currentState.equals(State.RESERVED);
	}
	public boolean isUnreserved() {
		return this.currentState.equals(State.UNRESERVED);
	}

}
