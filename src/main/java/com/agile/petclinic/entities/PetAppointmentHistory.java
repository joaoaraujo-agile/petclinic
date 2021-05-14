package com.agile.petclinic.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.agile.petclinic.entities.utils.Auditable;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tb_pethistory")
public class PetAppointmentHistory extends Auditable<String> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonIgnore
	@OneToOne
	@MapsId
	private Appointment historyAppointment;

	public PetAppointmentHistory() {
		super();
	}

	public PetAppointmentHistory(Long id, Appointment appointment) {
		super();
		this.id = id;
		this.historyAppointment = appointment;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Appointment getAppointment() {
		return historyAppointment;
	}

	public void setAppointment(Appointment appointment) {
		this.historyAppointment = appointment;
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
		PetAppointmentHistory other = (PetAppointmentHistory) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
