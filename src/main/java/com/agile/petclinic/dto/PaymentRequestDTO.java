package com.agile.petclinic.dto;

import java.io.Serializable;
import java.time.Instant;

import com.agile.petclinic.entities.Payment;
import com.agile.petclinic.entities.enums.PaymentType;

public class PaymentRequestDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Instant datetime;
	private Double amount;
	private Integer type;

	private Long appointmentId;

	public PaymentRequestDTO() {
		super();
	}

	public PaymentRequestDTO(Instant datetime, Double amount, PaymentType type, Long appointmentId) {
		super();
		this.datetime = datetime;
		this.amount = amount;
		setType(type);
		this.appointmentId = appointmentId;
	}
	
	public static PaymentRequestDTO toDTO (Payment obj) {
		return new PaymentRequestDTO(obj.getDatetime(), obj.getAmount(), obj.getType(), obj.getId());
	}

	public Instant getDatetime() {
		return datetime;
	}

	public Double getAmount() {
		return amount;
	}

	public PaymentType getType() {
		return PaymentType.valueOf(type);
	}

	public void setType(PaymentType type) {
		if (type != null) {
			this.type = type.getCode();
		}
	}

	public Long getAppointmentId() {
		return appointmentId;
	}

}
