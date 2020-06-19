package rentalapp;

import java.util.ArrayList;
import java.util.List;

public enum CarModel {
	
	COROLLA("Corolla"),
	CAMRY("Camry"),
	PRIUS("Prius"),
	CIVIC("Civic"),
	OUTBACK("Outback"),
	MUSTANG("Mustang"),
	//SUV
	FORESTER("Forester"),
	CRV("Crv"),
	RAV4("Rav4"),
	ACURA("Acura"),
	//VAN
	SIENNA("Sienna"),
	ODYSSEY("Odyssey"),
	CARAVAN("Caravan");
		
	static List<String> allowedValues = new ArrayList<>();
	
	static {
		for (final CarModel type : CarModel.values()) {
			allowedValues.add(type.getValue());
		}
	}

	private final String value;
	
	CarModel(final String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
	public static boolean isValid(String carModel) {
		if (allowedValues.contains(carModel)) {
			return true;
		} else {
			return false;
		}
	}

}
