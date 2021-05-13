package com.agile.petclinic.entities.enums;

public enum PaymentType {
	
	CASH(1);
	
	private int code;

	private PaymentType(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
	
	public static PaymentType valueOf(int code) {
		for (PaymentType value : PaymentType.values()) {
			if (value.getCode() == code) {
				return value;
			}
		}
		throw new IllegalArgumentException("Invalid PaymentType code");
	}

	public void setCode(int code) {
		this.code = code;
	}		
}
