package com.agile.petclinic.entities.enums;

public enum AppointmentType {
	
	MEDICAL(1),
	GROOMING(2);
	
	private int code;

	private AppointmentType(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
	
	public static AppointmentType valueOf(int code) {
		for (AppointmentType value : AppointmentType.values()) {
			if (value.getCode() == code) {
				return value;
			}
		}
		throw new IllegalArgumentException("Invalid AppointmentType code");
	}

	public void setCode(int code) {
		this.code = code;
	}		
}
