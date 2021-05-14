package com.agile.petclinic.dto;

import java.io.Serializable;
import java.time.Instant;

import com.agile.petclinic.entities.Appointment;
import com.agile.petclinic.entities.Payment;
import com.agile.petclinic.entities.enums.AppointmentType;

public class PetHistoryAppointmentDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Instant datetime;
	private String description;
	private Integer type;
	private Payment payment;

	public PetHistoryAppointmentDTO() {
		super();
	}

	public PetHistoryAppointmentDTO(Long id, Instant datetime, String description, AppointmentType type,
			Payment payment) {
		super();
		this.id = id;
		this.datetime = datetime;
		this.description = description;
		setType(type);
		this.payment = payment;
	}

	static public PetHistoryAppointmentDTO toDTO(Appointment obj) {
		return new PetHistoryAppointmentDTO(obj.getId(), obj.getDatetime(), obj.getDescription(), obj.getType(),
				obj.getPayment());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Instant getDatetime() {
		return datetime;
	}

	public void setDatetime(Instant datetime) {
		this.datetime = datetime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public AppointmentType getType() {
		return AppointmentType.valueOf(type);
	}

	public void setType(AppointmentType type) {
		this.type = type.getCode();
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

}
