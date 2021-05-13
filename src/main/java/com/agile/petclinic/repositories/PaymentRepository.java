package com.agile.petclinic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agile.petclinic.entities.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
