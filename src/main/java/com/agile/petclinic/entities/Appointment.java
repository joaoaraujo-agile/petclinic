package com.agile.petclinic.entities;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.agile.petclinic.entities.enums.AppointmentType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tb_appointment")
public class Appointment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
	private Instant datetime;

	private String description;
	private Integer type;

	@ManyToOne
	@JoinColumn(name = "pet_id")
	private Pet pet;

	@OneToOne(mappedBy = "appointment", cascade = CascadeType.ALL, orphanRemoval = true)
	private Payment payment;

	@JsonIgnore
	@OneToOne(mappedBy = "historyAppointment", cascade = CascadeType.ALL, orphanRemoval = true)
	private PetAppointmentHistory history;

	public Appointment() {
		super();
	}

	public Appointment(Long id, Instant datetime, String description, AppointmentType type, Pet pet, Payment payment, PetAppointmentHistory history) {
		super();
		this.id = id;
		this.datetime = datetime;
		this.description = description;
		setType(type);
		this.pet = pet;
		this.payment = payment;
		this.history = history;
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
		if (type != null) {
			this.type = type.getCode();
		}
	}

	public Pet getPet() {
		return pet;
	}

	public void setPet(Pet pet) {
		this.pet = pet;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public PetAppointmentHistory getHistory() {
		return history;
	}

	public void setHistory(PetAppointmentHistory history) {
		this.history = history;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Appointment other = (Appointment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
