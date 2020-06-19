package rentalapp;

import java.util.ArrayList;
import java.util.List;

public enum CarMake {
	
	TOYOTA("Toyota"),
	HONDA("Honda"),
	SUBARU("Subaru"),
	NISSAN("Nissan"),
	DODGE("Dodge"),
	FORD("Ford");
		
	static List<String> allowedValues = new ArrayList<>();
	
	static {
		for (final CarMake type : CarMake.values()) {
			allowedValues.add(type.getValue());
		}
	}

	private final String value;
	
	CarMake(final String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
	public static boolean isValid(String carMake) {
		if (allowedValues.contains(carMake)) {
			return true;
		} else {
			return false;
		}
	}

}
