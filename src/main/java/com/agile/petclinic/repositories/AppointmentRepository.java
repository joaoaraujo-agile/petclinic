package com.agile.petclinic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agile.petclinic.entities.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

}
