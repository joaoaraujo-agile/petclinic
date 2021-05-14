package com.agile.petclinic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agile.petclinic.entities.PaymentHistory;

public interface PaymentHistoryRepository extends JpaRepository<PaymentHistory, Long> {

}
