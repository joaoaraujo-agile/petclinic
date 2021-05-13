package com.agile.petclinic.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.agile.petclinic.entities.User;

@SpringBootTest
class UserServiceTest {

	@Autowired
	private UserService service;

	static User usr1;
	static User usr2;
	
	@BeforeAll
	static void initAll() {
		usr1 = new User(1L, "admin", "admin", null);
		usr2 = new User(2L, "user", "user", null);
	}

	@Test
	void shouldReturnAll() {
		List<User> list = Arrays.asList(usr1, usr2);
		assertEquals(list, service.findAll());
	}
	
	@Test
	void shouldReturnOne() {
		assertEquals(usr1, service.findById(usr1.getId()));
	}
	
	@Test
	void shouldInsertOne() {
		String password = "mary456";
		User pet4 = new User(null, "Mary", "mary", password);
		User obj = service.insert(pet4);
		assertEquals(pet4, obj);
		assertNotEquals(password, obj.getPassword());
	}

	@Test
	void shouldDeleteOne() {
		service.delete(3L);
	}
	
	@Test
	@Transactional
	void shouldUpdateOne() {
		usr2.setName("John");
		User obj = service.update(usr2.getId(), usr2);
		assertEquals(usr2.getName(), obj.getName());
	}
}
