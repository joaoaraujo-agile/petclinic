package com.agile.petclinic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.agile.petclinic.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

	@Transactional(readOnly=true)
	User findByUsername(String username);
}
