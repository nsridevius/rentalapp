package rentalapp;

import java.util.ArrayList;
import java.util.List;

public enum CarType {
	SEDAN("Sedan"),
	SUV("Suv"),
	VAN("Van");
		
	static List<String> allowedValues = new ArrayList<>();
	
	static {
		for (final CarType type : CarType.values()) {
			allowedValues.add(type.getValue());
		}
	}

	private final String value;
	
	CarType(final String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
	public static boolean isValid(String carType) {
		if (allowedValues.contains(carType)) {
			return true;
		} else {
			return false;
		}
	}

}
