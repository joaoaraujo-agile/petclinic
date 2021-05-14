package com.agile.petclinic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agile.petclinic.entities.PetAppointmentHistory;

public interface PetAppointmentHistoryRepository extends JpaRepository<PetAppointmentHistory, Long> {

}
